package com.cas.ws.service.impl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cas.ws.io.document.UserDocument;
import com.cas.ws.io.repositories.UserRepository;
import com.cas.ws.service.UserService;
import com.cas.ws.shared.Utils;
import com.cas.ws.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Utils utils;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {

		if (userRepository.findByEmail(userDto.getEmail()) != null)
			throw new RuntimeException("record already Exists");
		ModelMapper modelMapper = new ModelMapper();
		UserDocument userDoc = modelMapper.map(userDto, UserDocument.class);
		String publicUserId = utils.generateStringId(50);
		userDoc.setUserId(publicUserId);
		userDoc.setEncryptedPassword(bCryptPasswordEncoder.encode(userDoc.getPassword()));
		userDoc.setEmailVerificationToken(Utils.generateEmailVerificationToken(publicUserId));
		UserDocument sotredUser = userRepository.save(userDoc);

		UserDto returnValue = modelMapper.map(sotredUser, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDocument userDoc = userRepository.findByEmail(email);

		if (userDoc == null)
			throw new UsernameNotFoundException(email);

//		return new UserPrincipal(userDoc);
		 return new User(
				 userDoc.getEmail(), 
				 userDoc.getEncryptedPassword(),
				 userDoc.getEmailVerificationStatus(), 
				 true, true, true, 
				 new ArrayList<>());
//		return new User(userDoc.getEmail(), userDoc.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUserByEmail(String email) {
		UserDocument userDoc = userRepository.findByEmail(email);
		if (userDoc == null)
			throw new UsernameNotFoundException(email);
		ModelMapper modelMapper = new ModelMapper();
		UserDto returnValue = modelMapper.map(userDoc, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue = new UserDto();
		UserDocument userDoc = userRepository.findByUserId(userId);
		if (userDoc == null)
			throw new UsernameNotFoundException(userId);

		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userDoc, UserDto.class);
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		ModelMapper modelMapper = new ModelMapper();
		if (userRepository.findByUserId(userId) == null)
			throw new RuntimeException("Record is not exisit");
		UserDocument userDoc = modelMapper.map(userDto, UserDocument.class);
		userDoc.setUserId(userId);
		UserDocument sotredUser = userRepository.save(userDoc);

		UserDto returnValue = modelMapper.map(sotredUser, UserDto.class);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserDocument userDoc = userRepository.findByUserId(userId);
		if (userDoc == null)
			throw new RuntimeException("Record is not exisit");

		userRepository.delete(userDoc);

	}

	@Override
	public boolean verifyEmailToken(String token) {
	    boolean returnValue = false;

        // Find user by token
        UserDocument userDocument = userRepository.findUserByEmailVerificationToken(token);

        if (userDocument != null) {
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if (!hastokenExpired) {
            	userDocument.setEmailVerificationToken(null);
            	userDocument.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userDocument);
                returnValue = true;
            }
        }

        return returnValue;
	}

	@Override
	public boolean requestPasswordReset(String email) {
		  boolean returnValue = true;
	        
	        UserDocument userDoc = userRepository.findByEmail(email);

	        if (userDoc == null) {
	            return returnValue;
	        }
	        
	        String token = new Utils().generatePasswordResetToken(userDoc.getUserId());
	        
	        userDoc.setPasswordResetToken(token);        
	        
	        userRepository.save(userDoc);
	        
//	        returnValue = new AmazonSES().sendPasswordResetRequest(
//	                userEntity.getFirstName(), 
//	                userEntity.getEmail(),
//	                token);
	        
			return returnValue;
	}

	@Override
	public boolean resetPassword(String token, String password) {
        boolean returnValue = false;
        
        if( Utils.hasTokenExpired(token) )
        {
            return returnValue;
        }
 
        UserDocument userDoc = userRepository.findByPasswordResetToken(token);

        if (userDoc == null) {
            return returnValue;
        }

        // Prepare new password
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        
        // Update User password in database
        userDoc.setEncryptedPassword(encodedPassword);
        UserDocument savedUserDoc = userRepository.save(userDoc);
 
        // Verify if password was saved successfully
        if (savedUserDoc != null && savedUserDoc.getEncryptedPassword().equalsIgnoreCase(encodedPassword)) {
            returnValue = true;
        }
        
        return returnValue;
	}

}

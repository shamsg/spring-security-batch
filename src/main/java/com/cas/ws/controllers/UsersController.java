package com.cas.ws.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cas.ws.model.request.PasswordResetModel;
import com.cas.ws.model.request.PasswordResetRequestModel;
import com.cas.ws.model.request.UserSignUpRequest;
import com.cas.ws.model.response.ErrorMessages;
import com.cas.ws.model.response.OperationStatusModel;
import com.cas.ws.model.response.RequestOperationStatus;
import com.cas.ws.model.response.UserRest;
import com.cas.ws.service.UserService;
import com.cas.ws.shared.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserRest createUser(@RequestBody UserSignUpRequest userSginUpReq) throws Exception {
		UserRest returnValue = new UserRest();

//		if(userSginUpReq.getFirstName().isEmpty()) throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
//		if(userSginUpReq.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		if (userSginUpReq.getFirstName().isEmpty())
			throw new NullPointerException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userSginUpReq, UserDto.class);

		UserDto createdUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserRest updateUser(@RequestBody UserSignUpRequest userSginUpReq, @PathVariable String id) {
		UserRest returnValue = new UserRest();
		ModelMapper modelMapper = new ModelMapper();

		UserDto userDto = modelMapper.map(userSginUpReq, UserDto.class);

		UserDto createdUser = userService.updateUser(id, userDto);
		returnValue = modelMapper.map(createdUser, UserRest.class);

		return returnValue;
	}

	@GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserRest getUser(@PathVariable String id) {
		UserRest returnValue = new UserRest();

		UserDto userdto = userService.getUserByUserId(id);

		ModelMapper modelMapper = new ModelMapper();
		returnValue = modelMapper.map(userdto, UserRest.class);

		return returnValue;
	}

	@DeleteMapping(
			path = "/{id}", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}

//	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
	// @PreAuthorize("hasAuthority('DELETE_AUTHORITY')")
	// @Secured("ROLE_ADMIN")
//	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
//	@ApiImplicitParams({
//		@ApiImplicitParam(name="authorization", value="${userController.authorizationHeader.description}", paramType="header")
//	})
//	public OperationStatusModel deleteUser(@PathVariable String id) {
//		OperationStatusModel returnValue = new OperationStatusModel();
//		returnValue.setOperationName(RequestOperationName.DELETE.name());
//
//		userService.deleteUser(id);
//
//		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
//		return returnValue;
//	}

	
    @GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        
        boolean isVerified = userService.verifyEmailToken(token);
        
        if(isVerified)
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }
    
    @PostMapping(path = "/password-reset-request", 
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
    	OperationStatusModel returnValue = new OperationStatusModel();
 
        boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());
        
        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
 
        if(operationResult)
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }
    
    @PostMapping(path = "/password-reset",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
    	OperationStatusModel returnValue = new OperationStatusModel();
 
        boolean operationResult = userService.resetPassword(
                passwordResetModel.getToken(),
                passwordResetModel.getPassword());
        
        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
 
        if(operationResult)
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }



}

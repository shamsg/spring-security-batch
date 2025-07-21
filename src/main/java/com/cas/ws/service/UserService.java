package com.cas.ws.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.cas.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
	public UserDto createUser(UserDto user);
	public UserDto getUserByEmail(String email);
	public UserDto getUserByUserId(String userId);
	public UserDto updateUser(String id, UserDto userDto);
	public void deleteUser(String id);
	public boolean verifyEmailToken(String token);
	public boolean requestPasswordReset(String email);
	public boolean resetPassword(String token, String password);
}

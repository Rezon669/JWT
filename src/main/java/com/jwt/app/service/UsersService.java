package com.jwt.app.service;

import com.jwt.app.entity.Users;
import com.jwt.app.exception.CustomException;

public interface UsersService {

//	String loadUserByUsername(String username) throws NameNotFoundException;

	String createUsers(Users user) throws CustomException;

	String loginValidation(String email, String password) throws CustomException;

	void updatePassword(String password, String confirmpassword, String email);


}

package com.jwt.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jwt.app.entity.Login;
import com.jwt.app.entity.Users;
import com.jwt.app.exception.CustomException;
import com.jwt.app.repository.UsersRepository;
import com.jwt.app.service.UsersService;

@Controller
@RequestMapping("/jwt/user")

public class UsersController {

	private static final Logger logger = LogManager.getLogger(UsersController.class);
	public String jwttoken;
	@Autowired
	UsersRepository usersRepository;

	@Autowired
	private UsersService usersService;

	@GetMapping("/signin")
	public String showLoginpage() {
		return "signin";
	}

	@PostMapping("/createaccount")
	public String createUser(Users user, Model model) {
		// method to create account

		try {

			usersService.createUsers(user);
			logger.info("User registred Successfully");

			return "backtologin";

		} catch (CustomException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);

			return "signup";

		}

	}

	@PostMapping("/loginvalidation")
	// method to perform login validation
	public String loginValidation(Login login, Model model, HttpServletResponse response) {
		try {
			jwttoken = usersService.loginValidation(login.getEmailid(), login.getPassword());

			if (jwttoken.isEmpty()) {

				model.addAttribute("errorMessage", "invalid Login Credentials");
				return "signin";

			}

			Cookie jwtcookie = new Cookie("token", jwttoken);

			// Set additional cookie attributes
			jwtcookie.setHttpOnly(true); // Makes the cookie accessible only by the server
			jwtcookie.setSecure(true); // Ensures the cookie is sent only over HTTPS
			jwtcookie.setPath("/"); // Restricts the cookie to the root path
			jwtcookie.setMaxAge(3600);

			response.addCookie(jwtcookie);
			return "welcome";

		} catch (CustomException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			return "signin";
		}
	}

	@GetMapping("/public/logout")
	public String logout(HttpServletResponse response) {
		// method to clear the cookies after logout
		Cookie jwtCookie = new Cookie("token", "");
		jwtCookie.setMaxAge(0);
		jwtCookie.setPath("/");
		response.addCookie(jwtCookie);
		return "signin"; // redirect to the signin page after logout
	}

	@GetMapping("/resetpassword")
	public String emailVerification() {
		return "resetpassword";
	}

	@PostMapping("/updatepassword")
	public String resetPassword(@RequestParam(value = "emailid", required = true) String emailid,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "confirmpassword", required = true) String confirmpassword, Model model) {
		try {

			// usersservice.emailVerification(emailid);
			usersService.updatePassword(password, confirmpassword, emailid);

			logger.info("Password updated successfully");
			return "passwordupdate";

		} catch (IllegalArgumentException e) {

			model.addAttribute("errorMessage", e.getMessage());
			logger.error(e);
			return "resetpassword";
		}
	}

	@GetMapping("/signup")
	public String signup() {

		return "signup";
	}

	@GetMapping("/backtohome")
	public String backtoHome() {

		return "welcome";

	}

}

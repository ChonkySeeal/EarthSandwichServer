package com.EarthSandwich.rest;

import java.time.LocalDateTime;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EarthSandwich.entity.User;
import com.EarthSandwich.security.JWT.JwtRequest;
import com.EarthSandwich.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;

	}

	@PostMapping("/register")
	public void registerUser(@RequestBody User user) {
		user.setId(0);
		LocalDateTime current = LocalDateTime.now();
		user.setDate(current);
		user.setModified_date(current);
		user.setVerified(0);
		userService.registerUser(user);
	}

	@PostMapping("/auth")
	public void loginUser(@RequestBody JwtRequest request, HttpServletResponse response) throws Exception {
		Cookie[] cookies = userService.authenticateUser(request.getEmail(), request.getPassword());
		response.addCookie(cookies[0]);
		response.addCookie(cookies[1]);
	}

	@GetMapping("/confirm/{token}")
	public void confirm(@PathVariable("token") String token) {
		userService.confirmUserEmail(token);
	}

}

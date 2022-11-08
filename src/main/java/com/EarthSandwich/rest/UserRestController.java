package com.EarthSandwich.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EarthSandwich.entity.User;
import com.EarthSandwich.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public User findById(int id) {
		return userService.findById(id);
	}
	
	@

}

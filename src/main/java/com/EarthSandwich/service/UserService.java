package com.EarthSandwich.service;

import javax.servlet.http.Cookie;

import com.EarthSandwich.entity.User;

public interface UserService {

	public User findById(int id);

	public User findByName(String name);

	public void registerUser(User user);

	public void confirmUserEmail(String token);

	public void sendPasswordResetEmail(String email);

	public void confirmUserPasswordLink(String passwordToken, String userEmail, String oldPassword, String newPassword);

	public Cookie[] deleteAccount(String email);

	public User findByEmail(String email);

	public Cookie[] authenticateUser(String email, String pass);

	public Cookie[] logoutUser();

}

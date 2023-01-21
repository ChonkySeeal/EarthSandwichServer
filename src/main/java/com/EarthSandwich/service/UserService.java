package com.EarthSandwich.service;

import javax.servlet.http.Cookie;

import com.EarthSandwich.entity.User;

public interface UserService {

	public User findById(int id);

	public User findByName(String name);

	public void save(User user);

	public Cookie[] deleteAccount(String email);

	public User findByEmail(String email);

	public Cookie[] authenticateUser(String email, String pass);

	public Cookie[] logoutUser();

	public void changePassword(String oldPassword, String newPassword, String email);

}

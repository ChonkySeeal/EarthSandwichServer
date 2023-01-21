package com.EarthSandwich.dao;

import com.EarthSandwich.entity.User;

public interface UserDAO {

	public User findById(int id);

	public User findByName(String name);

	public void save(User user);

	public void deleteById(int id);

	public User findByEmail(String email);

	public boolean findExistUser(String email, String pass);
}

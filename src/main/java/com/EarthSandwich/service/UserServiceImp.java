package com.EarthSandwich.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.EarthSandwich.dao.UserDAO;
import com.EarthSandwich.entity.User;

@Service
public class UserServiceImp implements UserService {

	private UserDAO userDAO;

	public UserServiceImp(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	@Transactional
	public List<User> findAll() {

		return userDAO.findAll();
	}

	@Override
	@Transactional
	public User findById(int id) {

		return userDAO.findById(id);
	}

	@Override
	@Transactional
	public void save(User user) {

		userDAO.save(user);

	}

	@Override
	@Transactional
	public void deleteById(int id) {

		userDAO.deleteById(id);

	}

}

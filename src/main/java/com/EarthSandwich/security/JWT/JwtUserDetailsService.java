package com.EarthSandwich.security.JWT;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.EarthSandwich.dao.UserDAO;
import com.EarthSandwich.entity.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private UserDAO userDAO;

	@Autowired
	public JwtUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDAO.findByEmail(email);

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or password is wrong");
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				new ArrayList<>());
	}

}

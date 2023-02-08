package com.EarthSandwich.service;

import java.time.LocalDateTime;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.EarthSandwich.dao.UserDAO;
import com.EarthSandwich.entity.User;
import com.EarthSandwich.security.JWT.JwtTokenUtil;
import com.EarthSandwich.security.password.PasswordAction;

@Service
public class UserServiceImp implements UserService {

	private UserDAO userDAO;

	private PasswordAction passwordAction;

	private JwtTokenUtil jwtTokenUtil;

	private EmailService emailService;

	@Value("${email.url}")
	private String url;

	@Autowired
	public UserServiceImp(UserDAO userDAO, PasswordAction passwordAction, JwtTokenUtil jwtTokenUtil,
			EmailService emailService) {
		this.userDAO = userDAO;
		this.passwordAction = passwordAction;
		this.jwtTokenUtil = jwtTokenUtil;
		this.emailService = emailService;
	}

	@Transactional
	@Override
	public User findById(int id) {
		return userDAO.findById(id);
	}

	@Transactional
	@Override
	public void registerUser(User user) {
		if (userDAO.findByName(user.getUsername()) != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"username \'" + user.getUsername() + "\' is already taken");
		}

		if (userDAO.findByEmail(user.getEmail()) != null && user.getVerified() == 1) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email is already registered");
		}

		if (!passwordAction.validatePass(user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Password must contain at least one number, one uppercase, one lowercase, one special character (!@#$%&), and needs to be between 8-16 characters long");
		}

		user.setPassword(passwordAction.encryptPass(user.getPassword()));
		userDAO.save(user);
		String link = String.format("%s/user/confirmEmail?token=%s", url,
				jwtTokenUtil.generateEmailToken(user.getEmail()));
		String email = emailService.buildEmailValidationEmail(user.getUsername(), link);
		emailService.sendEmail(user.getEmail(), email, "Please Verify Your Password");
	}

	@Override
	@Transactional
	public void confirmUserEmail(String token) {
		if (!jwtTokenUtil.isTokenExpired(token)) {
			String email = jwtTokenUtil.getEmailFromToken(token);
			User user = userDAO.findByEmail(email);
			user.setVerified(1);
			userDAO.save(user);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The link is not valid");
		}
	}

	@Override
	@Transactional
	public void sendPasswordResetEmail(String email) {
		String link = String.format("%s/user/reset?token=%s", url, jwtTokenUtil.generateEmailToken(email));
		String emailContent = emailService.buildPasswordResetEmail(email, link);
		emailService.sendEmail(email, emailContent, "Reset Your Password");
	};

	@Override
	@Transactional
	public void confirmUserPasswordLink(String passwordToken, String userEmail, String oldPassword,
			String newPassword) {
		if (!jwtTokenUtil.isTokenExpired(passwordToken)) {
			String tokenEmail = jwtTokenUtil.getEmailFromToken(passwordToken);
			if (tokenEmail.equals(userEmail)) {
				User user = userDAO.findByEmail(userEmail);

				if (!passwordAction.passwordVerify(oldPassword, user.getPassword()))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CurrentPassword is not matched");
				if (!passwordAction.validatePass(newPassword))
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid new password");
				LocalDateTime current = LocalDateTime.now();
				user.setModified_date(current);
				user.setPassword(passwordAction.encryptPass(newPassword));
				userDAO.save(user);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unauthorized user attempt");
			}

		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The link is not valid");
		}
	}

	@Transactional
	@Override
	public Cookie[] deleteAccount(String email) {
		User user = userDAO.findByEmail(email);
		if (user == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

		userDAO.deleteById(user.getId());
		Cookie[] cookies = new Cookie[2];
		cookies[0] = new Cookie("accessToken", "logoutmember.logoutmember.logoutmember");
		cookies[0].setMaxAge(0);
		cookies[0].setHttpOnly(true);
		cookies[0].setPath("/");
		cookies[0].setSecure(true);
		cookies[0].setDomain("earthsandwich.lol");
		cookies[1] = new Cookie("loginstatus", "false");
		cookies[1].setMaxAge(0);
		cookies[1].setPath("/");
		cookies[1].setSecure(true);
		cookies[1].setDomain("earthsandwich.lol");
		return cookies;

	}

	@Transactional
	@Override
	public User findByName(String name) {

		return userDAO.findByName(name);
	}

	@Transactional
	@Override
	public User findByEmail(String email) {

		return userDAO.findByEmail(email);
	}

	@Override
	public Cookie[] authenticateUser(String email, String pass) {

		User user = userDAO.findByEmail(email);
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or password is invalid");
		}
		if (!passwordAction.passwordVerify(pass, user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email or password is invalid");
		}

		if (user.getVerified() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you must verify your email");
		}
		final String token = jwtTokenUtil.generateToken(user);

		if (user.getVerified() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you need to confirm your email");
		}
		Cookie[] cookies = new Cookie[2];
		cookies[0] = new Cookie("accessToken", token);
		cookies[0].setMaxAge(24 * 60 * 60);
		cookies[0].setHttpOnly(true);
		cookies[0].setPath("/");
		cookies[0].setSecure(true);
		cookies[0].setDomain("earthsandwich.lol");
		cookies[1] = new Cookie("loginstatus", user.getUsername());
		cookies[1].setMaxAge(24 * 60 * 60);
		cookies[1].setSecure(true);
		cookies[1].setDomain("earthsandwich.lol");
		cookies[1].setPath("/");

		return cookies;

	}

	@Override
	public Cookie[] logoutUser() {

		Cookie[] cookies = new Cookie[2];
		cookies[0] = new Cookie("accessToken", "logoutmember.logoutmember.logoutmember");
		cookies[0].setMaxAge(0);
		cookies[0].setHttpOnly(true);
		cookies[0].setPath("/");
		cookies[0].setSecure(true);
		cookies[0].setDomain("earthsandwich.lol");
		cookies[1] = new Cookie("loginstatus", "false");
		cookies[1].setMaxAge(0);
		cookies[1].setPath("/");
		cookies[1].setSecure(true);
		cookies[1].setDomain("earthsandwich.lol");
		return cookies;
	}

}

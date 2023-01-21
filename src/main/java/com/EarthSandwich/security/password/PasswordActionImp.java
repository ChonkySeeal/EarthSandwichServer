package com.EarthSandwich.security.password;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordActionImp implements PasswordAction {

	private PasswordEncoder passwordencoder;

	@Autowired
	public PasswordActionImp(PasswordEncoder passwordencoder) {
		this.passwordencoder = passwordencoder;
	}

	@Override
	public boolean validatePass(String pass) {
		String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@#$%&]).{8,16}";

		Pattern pattern = Pattern.compile(regex);

		if (pass == null) {
			return false;
		}

		Matcher m = pattern.matcher(pass);

		return m.matches();
	}

	@Override
	public String encryptPass(String s) {

		return passwordencoder.encode(s);
	}

	@Override
	public boolean passwordVerify(String r, String e) {

		return passwordencoder.matches(r, e);
	}

}

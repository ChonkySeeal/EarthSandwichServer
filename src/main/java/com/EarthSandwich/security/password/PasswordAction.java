package com.EarthSandwich.security.password;

public interface PasswordAction {
	public boolean validatePass(String s);

	public String encryptPass(String s);

	public boolean passwordVerify(String r, String e);
}

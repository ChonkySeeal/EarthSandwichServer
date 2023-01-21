package com.EarthSandwich.dto;

public class PasswordRequestDTO {
	private String oldpassword;
	private String newpassword;

	public PasswordRequestDTO(String oldpassword, String newpassword) {

		this.oldpassword = oldpassword;
		this.newpassword = newpassword;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public String getNewpassword() {
		return newpassword;
	}

}

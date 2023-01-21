package com.EarthSandwich.dto;

public class UserResponseDTO {
	private String email;
	private String username;

	public UserResponseDTO(String email, String username) {
		this.email = email;
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

}

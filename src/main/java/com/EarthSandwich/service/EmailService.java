package com.EarthSandwich.service;

public interface EmailService {
	public String buildEmailValidationEmail(String name, String link);

	public String buildPasswordResetEmail(String name, String link);

	public void sendEmail(String to, String email, String topic);
}

package com.capstone;

public class EmailVerificationFactory {

	public static final String SIGNUP = "signup";
	public static final String FORGOT_PASSWORD = "forgot_password";
	
	public EmailVerification getEmaiVerification (String emailVerificationType, String username) {
		
		if (emailVerificationType.equalsIgnoreCase("signup")) {
			return new SignUpEmailVerification(username);
		} else if (emailVerificationType.equalsIgnoreCase("forgot_password")) {
			return new ForgotPasswordEmailVerification(username);
		}
		
		return null;
	}
	
}

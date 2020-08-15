package com.capstone;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserAuthentication {
	
	private static UserAuthentication instance = null;
	
	private Database database;
	
	private UserAuthentication () {
		database = new Database();
	}
	
	public static synchronized UserAuthentication getUserAuthenicationInstance () {
		
		if (instance == null) {
			instance = new UserAuthentication ();
		}
		
		return instance;
	}
	
	public String generateVerificationCode (int length) {
		//Code taken from https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
		String alphaNumeric = "ABCDEFGHIJKLMNPQRSTUVWXYZ"
                + "123456789";
		
		StringBuilder stringBuilder = new StringBuilder(length);
		
		for (int i = 0; i < length; i++) {
			int index = (int) (alphaNumeric.length() * Math.random());
			stringBuilder.append(alphaNumeric.charAt(index));
		}
		
		return stringBuilder.toString();
	}
	
	public String validUser (User user, String passwordConfirm) {
		
		database = new Database();
		
		if (user.getFirstName().equals("")) {
			return "First name is empty";
		}
		
		if (user.getLastName().equals("")) {
			return "Last name is empty";
		}
		
		if (user.getUsername().equals("")) {
			return "Username is empty";
		}
		
		if (user.getEmail().equals("")) {
			return "Email is empty";
		}
		
		if (user.getPassword().equals("")) {
			return "Password is empty";
		}
		
		if (!user.getPassword().equals(passwordConfirm)) {
			return "Passwords do not match";
		}
		
		if (!database.validUser(user)) {
			return "Username is taken";
		}
		
		return "valid";
	}
	
	public void addUser (User user) {
		String code = "NOCODE";
		database.addUserToDatabase(user, code);
	}	
	
	public boolean verifyUser (String username, String enteredCode) {
		
		String code = database.getUserVerificationCode(username);
		
		if (code.equalsIgnoreCase(enteredCode)) {
			database.verifiyUser(username);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean changeUserPass (String username, String enteredCode, String password, String confirmPassword) {
		if (verifyUser(username, enteredCode)) {
			if (password.equals(confirmPassword)) {
				database.updatePassword(username, password);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean loginUser (String username, String password) {
		if (database.login(username, password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isUserVerified (String username) {
		return database.isUserVerified(username);
	}
}

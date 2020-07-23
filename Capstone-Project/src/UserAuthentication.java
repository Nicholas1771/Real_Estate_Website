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
		this.database = new Database();
	}
	
	public static synchronized UserAuthentication getUserAuthenicationInstance () {
		
		if (instance == null) {
			instance = new UserAuthentication ();
		}
		
		return instance;
	}
	
	public void sendVerificationEmail(String username) {
		
		String to = database.getUserEmail(username);
		String from = "capstoneprojectwebapp@gmail.com";
		String code = database.getUserVerificationCode(username);
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", 587);
		
		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication () {
				return new PasswordAuthentication("capstoneprojectwebapp@gmail.com", "Capstone.34");						
			}
		});
		
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Verification Email");
			message.setText("Thank you for signing up with our web application, your verification code is: " + code);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public void addUser (User user, String code) {
		database.addUserToDatabase(user, code);
	}	
	
	public boolean verifyUser (String username, String enteredCode) {
		
		String code = database.getUserVerificationCode(username);
		
		if (code.equals(enteredCode)) {
			database.verifiyUser(username);
			return true;
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

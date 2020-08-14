package com.capstone;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpEmailVerification implements EmailVerification {
	
	private String username;
	
	private Database database;
	
	public SignUpEmailVerification (String username) {
		this.username = username;
		database = new Database();
	}
	
	@Override
	public void send() {
		String to = database.getUserEmail(username);
		String from = "capstoneprojectwebapp@gmail.com";
		String code = UserAuthentication.getUserAuthenicationInstance().generateVerificationCode(6);
		
		database.setNewVerificationCode(username, code);
		
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
			message.setSubject("Capstone: Verify your email");
			message.setText("Thank you for signing up with our real estate web application! We still need to verify your email address. You will need to enter this code when you login:\n" + code);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

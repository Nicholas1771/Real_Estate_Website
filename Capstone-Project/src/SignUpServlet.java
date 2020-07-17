import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.MimeMessage;

public class SignUpServlet extends HttpServlet {

	private Database database;
	
	@Override
	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String htmlResponse = 
				"<html>"
				+ "<head>"
				+ "</head>"
				+ "<body>"
				+ "Sign Up<br>"
				+ "<form method=\"post\">"
				+ "<label for=\"firstName\">First Name:</label><br>"
				+ "<input type=text id=\"firstName\" name=\"firstName\"><br>"
				+ "<label for=\"lastName\">Last Name:</label><br>"
				+ "<input type=text id=\"lastName\" name=\"lastName\"><br>"
				+ "<label for=\"email\">Email:</label><br>"
				+ "<input type=email id=\"email\" name=\"email\"><br>"
				+ "<label for=\"username\">Username:</label><br>"
				+ "<input type=text id=\"username\" name=\"username\"><br>"
				+ "<label for=\"password\">Password:</label><br>"
				+ "<input type=password id=\"password\" name=\"password\"><br>"
				+ "<label for=\"passwordConfirm\">Confirm Password:</label><br>"
				+ "<input type=password id=\"passwordConfirm\" name=\"passwordConfirm\"><br>"
				+ "<input type=submit value=\"Sign Up\"><br>"
				+ "<a href=\"Login\">Login</a><br>"
				+ "</form>"
				+ "</body>"
				+ "</html>";
		
		PrintWriter writer = resp.getWriter();
		writer.write(htmlResponse);
	}
	
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		boolean verified = false;
		
		User user = new User(firstName, lastName, username, email, password, verified);
		
		String response = verifyUser(user, passwordConfirm);
		
		database = new Database();
		
		if (response.equals("verified")) {
			addUser(user);
			String htmlResponse = 
					"<html>"
					+ "<head>"
					+ "</head>"
					+ "<body>"
					+ "Successfully Signed Up!<br><br>"
					+ "<a href=\"Login\">Login</a><br>"
					+ "</form>"
					+ "</body>"
					+ "</html>";
			
			PrintWriter writer = resp.getWriter();
			writer.write(htmlResponse);
		} else {
			String htmlResponse = 
					"<html>"
					+ "<head>"
					+ "</head>"
					+ "<body>"
					+ "ERROR: " + response + "<br><br>"
					+ "Sign Up<br>"
					+ "<form method=\"post\">"
					+ "<label for=\"firstName\">First Name:</label><br>"
					+ "<input value=" + firstName + " type=text id=\"firstName\" name=\"firstName\"><br>"
					+ "<label for=\"lastName\">Last Name:</label><br>"
					+ "<input value=" + lastName + " type=text id=\"lastName\" name=\"lastName\"><br>"
					+ "<label for=\"email\">Email:</label><br>"
					+ "<input value=" + email + " type=email id=\"email\" name=\"email\"><br>"
					+ "<label for=\"username\">Username:</label><br>"
					+ "<input value=" + username + " type=text id=\"username\" name=\"username\"><br>"
					+ "<label for=\"password\">Password:</label><br>"
					+ "<input type=password id=\"password\" name=\"password\"><br>"
					+ "<label for=\"passwordConfirm\">Confirm Password:</label><br>"
					+ "<input type=password id=\"passwordConfirm\" name=\"passwordConfirm\"><br>"
					+ "<input type=submit value=\"Sign Up\"><br>"
					+ "<a href=\"Login\">Login</a><br>"
					+ "</form>"
					+ "</body>"
					+ "</html>";
			
			PrintWriter writer = resp.getWriter();
			writer.write(htmlResponse);
		}
	}
	
	private String verifyUser (User user, String passwordConfirm) {
		
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
		
		//send confirmation email here
		
		
		if (!database.validUser(user)) {
			return "Username is taken";
		}
		
		//Any other verifications we need to make can go here
		
		return "verified";
	}
	
	private void sendVerificationEmail(User user) {
		String to = user.getEmail();
		String from = "Nicholasiozzo17@gmail.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		
		Session session = Session.getDefaultInstance(properties);
		
		try {
			MimeMessage message = new MimeMessage(session);
		}
	}
	
	private void addUser (User user) {
		database.addUserToDatabase(user);
	}
	
}

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	
	private Database database;
	
	@Override
	protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String htmlResponse = 
				"<html>"
				+ "<head>"
				+ "</head>"
				+ "<body>"
				+ "Login<br>"
				+ "<form method=\"post\">"
				+ "<label for=\"username\">Username:</label><br>"
				+ "<input type=text id=\"username\" name=\"username\"><br>"
				+ "<label for=\"password\">Password:</label><br>"
				+ "<input type=password id=\"password\" name=\"password\"><br>"
				+ "<input type=submit value=\"Login\"><br>"
				+ "<a href=\"SignUp\">Sign Up</a><br>"
				+ "</form>"
				+ "</body>"
				+ "</html>";
		
		PrintWriter writer = resp.getWriter();
		writer.write(htmlResponse);
	}
	
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String password = req.getParameter("password");
		String username = req.getParameter("username");
		
		database = new Database(new User("", "", "", "", ""));
		
		if (database.login(username, password)) {
			System.out.println("Successful Login");
			String htmlResponse = 
					"<html>"
					+ "<head>"
					+ "</head>"
					+ "<body>"
					+ "Welcome to our real estate website!<br>"
					+ "Successfuly logged in as: " + username
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
					+ "* Invalid username or password<br><br>"
					+ "Login<br>"
					+ "<form method=\"post\">"
					+ "<label for=\"username\">Username:</label><br>"
					+ "<input type=text id=\"username\" name=\"username\"><br>"
					+ "<label for=\"password\">Password:</label><br>"
					+ "<input type=password id=\"password\" name=\"password\"><br>"
					+ "<input type=submit value=\"Login\""
					+ "<a href=\"SignUp\">Sign Up</a><br>"
					+ "</form>"
					+ "</body>"
					+ "</html>";
			
			PrintWriter writer = resp.getWriter();
			writer.write(htmlResponse);
		}
	}
}

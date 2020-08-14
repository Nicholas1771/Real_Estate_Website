import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpServlet extends HttpServlet {

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
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String response = signupUser(user, passwordConfirm);
		
		if (response.equals("valid")) {
			out.print("Successfully signed up<br>");  
            req.getRequestDispatcher("login.html").include(req, resp);
		} else {
			out.print("Error: " + response + "<br>");  
            req.getRequestDispatcher("signup.html").include(req, resp);
		}
	}	
	
	private String signupUser (User user, String passwordConfirm) {	
		
		UserAuthentication userAuth = UserAuthentication.getUserAuthenicationInstance();
		
		String response = userAuth.validUser(user, passwordConfirm);				
		
		if (response.equals("valid")) {
			
			String code = userAuth.generateVerificationCode(6);
			
			userAuth.addUser(user, code);
			
			userAuth.sendVerificationEmail(user.getUsername());
		} 
		
		return response;
	}
}

package com.capstone;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private EmailVerificationFactory emailVerificationFactory;

	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String passwordConfirm = req.getParameter("passwordConfirm");
		
		User user = new UserBuilder()
			.setFirstName(firstName)
			.setLastName(lastName)
			.setEmail(email)
			.setPassword(password)
			.setUsername(username)
			.createUser();
		
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
			
			userAuth.addUser(user);
			
			String username = user.getUsername();
			
			emailVerificationFactory = new EmailVerificationFactory();
			
			EmailVerification email = emailVerificationFactory.getEmaiVerification(EmailVerificationFactory.SIGNUP, username);
			
			email.send();
		} 
		
		return response;
	}
}

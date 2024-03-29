package com.capstone;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VerifyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private EmailVerificationFactory emailVerificationFactory;
	
	private UserAuthentication userAuth;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		userAuth = UserAuthentication.getUserAuthenicationInstance();
		
		String username = (String) req.getSession().getAttribute("username");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		if (req.getParameter("verify").equals("Verify")) {
			
			String enteredCode = req.getParameter("code");
		
			boolean verified = verifyUser (username, enteredCode);
			
			if (verified) {
				resp.sendRedirect("/Capstone-Project/home.jsp");
			} else {
				out.print("Incorrect code<br>");  
				req.getRequestDispatcher("verify.html").include(req, resp);
			}
		} else if (req.getParameter("verify").equals("Resend Code")) {

			emailVerificationFactory = new EmailVerificationFactory();
			
			EmailVerification email = emailVerificationFactory.getEmaiVerification(EmailVerificationFactory.SIGNUP, username);
			
			email.send();
			
			out.println("Resent verification email<br>");  
			req.getRequestDispatcher("verify.html").include(req, resp);
		}
	}
	
	private boolean verifyUser (String username, String code) {		
		return userAuth.verifyUser(username, code);
	}
}

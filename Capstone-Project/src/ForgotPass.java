import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForgotPass extends HttpServlet {

	private UserAuthentication userAuth;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		userAuth = UserAuthentication.getUserAuthenicationInstance();
		
		String username = req.getParameter("username");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		if (req.getParameter("forgot").equals("Confirm")) {
			
			String enteredCode = req.getParameter("code");
			String password = req.getParameter("newPassword");
			String passwordConfirm = req.getParameter("newPasswordConfirm");
			
			boolean success = userAuth.changeUserPass(username, enteredCode, password, passwordConfirm);
			
			if (success) {
				out.print("Successfully changed password<br>"); 
				resp.sendRedirect("login.html");
			} else {
				out.print("Incorrect<br>");  
				req.getRequestDispatcher("forgot_password.html").include(req, resp);
			}
		} else if (req.getParameter("forgot").equals("Send Email")) {
			String message = "You requested a password reset from our web application. Your verification code is:";
			userAuth.sendVerificationEmail(username, message);	
			out.println("Sent verification code<br>");  
			req.getRequestDispatcher("forgot_password.html").include(req, resp);
		}
	}
}

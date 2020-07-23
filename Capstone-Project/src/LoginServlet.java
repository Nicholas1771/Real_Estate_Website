import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	
	@Override
	protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		String password = req.getParameter("password");
		String username = req.getParameter("username");
		
		resp.setContentType("text/html");
		
		PrintWriter out = resp.getWriter();
		
		String response = loginUser (username, password);
		
		
		
		if (response.equals("fail")) {
			
			out.print("Incorrect username or password<br>");  
            req.getRequestDispatcher("login.html").include(req, resp);
            
		} else {
			
			req.getSession().setAttribute("username", username);
			
			if (response.equals("verified")) {
				
				resp.sendRedirect("/Capstone-Project/main.html");
				
			} else if (response.equals("unverified")) {
				
				req.getRequestDispatcher("verify.html").include(req, resp);
			}
		}
	}
	
	private String loginUser (String username, String password) {
		
		UserAuthentication userAuth = UserAuthentication.getUserAuthenicationInstance();
		
		String response = null;
		
		if (userAuth.loginUser(username, password)) {
			
			if (userAuth.isUserVerified(username)) {
				
				response = "verified";
			} else {	
				
				response = "unverified";
			}
			
		} else {
			
			response = "fail";
		}
		
		return response;
	}
}

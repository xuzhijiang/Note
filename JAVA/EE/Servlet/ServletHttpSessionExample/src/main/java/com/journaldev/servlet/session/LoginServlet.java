package com.journaldev.servlet.session;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String usernameStr = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(usernameStr) && password.equals(pwd)){
			Cookie[] cookiesBeforeHttpSession = request.getCookies();

			// 如果在客户端禁用了cookie并且我们正在使用URL重写(URL rewriting)，
			// 则此方法使用request URL中的jsessionid值来查找相应的会话(corresponding session)。

			//JSESSIONID的cookie用于session跟踪
			// Returns the current session associated
			// with this request, or if the request does not have a session, creates one. 
			HttpSession session = request.getSession();
			session.setAttribute("session-name-user", "session-value-xzj");
			//setting session to expire in 30 mins
			session.setMaxInactiveInterval(30*60);

			Cookie userName = new Cookie("cookie-user", usernameStr);
			userName.setMaxAge(30*60);
			response.addCookie(userName);

			response.sendRedirect("LoginSuccess.jsp");
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
	}

}

package com.boot.servlet.core.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final String userID = "xzj";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {

		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			Cookie loginCookie = new Cookie("user", user);
			// setting cookie to expire in 30 mins
			loginCookie.setMaxAge(30*60);
			
			// 理想情况下，应该有一个复杂的逻辑来设置cookie值,以便session跟踪，以便它不会与任何其他请求冲突。
			// 也就是cookie的生成算法，避免不同请求的cookie相同
			
			// 将指定的cookie添加到响应中。 可以多次调用此方法来设置多个cookie。
			response.addCookie(loginCookie);

			// 我们为response设置的cookie, 然后将其转发(forwarding it)到LoginSuccess.jsp,
			// 此cookie将用于跟踪会话
			response.sendRedirect("templates/LoginSuccess.jsp");
			// 注意JSESSIONID这个cookie是自动添加的.
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/templates/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
	}

}

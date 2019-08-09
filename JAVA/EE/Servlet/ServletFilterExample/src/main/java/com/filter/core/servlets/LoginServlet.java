package com.filter.core.servlets;

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

@WebServlet(urlPatterns = "/login", name = "login-servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String userID = "admin";
	private final String password = "password";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			// 看此方法的解释,如果不传入参数: 如果session不存在,则创建一个新的session
			// 如果传入true: 如果session不存在,则创建一个新的session
			// 如果传入false: 如果session不存在,则当前请求无效,返回null
			HttpSession session = request.getSession();
			session.setAttribute("user", "admin");
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			
			Cookie userName = new Cookie("user", user);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			response.sendRedirect("LoginSuccess.jsp");
			// When client is authenticated, it’s forwarded to LoginSuccess.jsp
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/filter-welcome.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
	}

}

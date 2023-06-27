package com.session.core;

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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		if("admin".equals(username) && "password".equals(pwd)){
			Cookie[] cookies = request.getCookies();

			if (cookies != null && cookies.length > 0) {
				for (Cookie cookie : cookies) {
					System.out.println("*******cookie名字: " + cookie.getName() + ", Cookie值: " + cookie.getValue());
				}
			}

			// 返回和这个request相关联的session,如果这个request还没有session,就创建一个
			HttpSession session = request.getSession();
			// 如果传入false: 如果session不存在,则返回null
			// HttpSession session = request.getSession(false);
			session.setAttribute("session-name-user", "session-value-xzj");
			// session.setMaxInactiveInterval(50*60); // 设置session超时时间为50分钟

			Cookie userName = new Cookie("cookie-user", username);
			userName.setMaxAge(30*60);
			response.addCookie(userName);

			Cookie jsessionIdCookie = new Cookie("JSESSIONID", session.getId());
			// 修改tomcat的 默认名字为JSESSIONID的cookie 它的domain/path/过期时间 等信息.
			jsessionIdCookie.setMaxAge(10*60); // 10分钟
			response.addCookie(jsessionIdCookie);

			response.sendRedirect("LoginSuccess.jsp");
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
	}
}

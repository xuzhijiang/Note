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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private final String userID = "xzj";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			Cookie loginCookie = new Cookie("user", user);
			//setting cookie to expire in 30 mins
			loginCookie.setMaxAge(30*60);
			
			// 我们为response设置的cookie, 然后将其转发(forwarding it)到LoginSuccess.jsp
			// 此cookie将用于跟踪会话
			
			// 另外： 理想情况下，应该有一个复杂的逻辑来设置cookie值,以便session跟踪，以便它不会与任何其他请求冲突。
			// 也就是cookie的生成算法，避免不同请求的cookie相同
			
			// 将指定的cookie添加到响应中。 可以多次调用此方法来设置多个cookie。
			response.addCookie(loginCookie);
			// cookie: the Cookie to return to the client.(返回客户端的cookie)
			
			response.sendRedirect("LoginSuccess.jsp");
			
			// 当我们点击Logout按钮时，我们应确保从客户端浏览器中删除cookie。
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
	}

}

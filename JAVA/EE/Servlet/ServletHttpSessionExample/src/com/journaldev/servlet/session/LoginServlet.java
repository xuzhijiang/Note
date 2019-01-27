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

// 让我们看一下使用HttpSession对象的session management示例
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	// LoginServlet servlet将创建会话并设置我们可以在其他资源或将来的请求中使用的属性。
	
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			
			//Understanding JSESSIONID Cookie

//			当我们使用HttpServletRequest getSession（）方法的时候，它会创建一个新请求，
//			并且它会创建新的HttpSession对象，并将具有name是JSESSIONID，value是session id的
//			Cookie添加到response object中，此cookie用于在将来的来自客户端的进一步请求中标识HttpSession对象。 

//			如果在客户端禁用了cookie并且我们正在使用URL重写(URL rewriting)，
			//则此方法使用request URL中的jsessionid值来查找相应的会话(corresponding session)。 
			//JSESSIONID cookie用于session跟踪，因此我们不应将其用于我们的应用程序目的，
			//以避免任何与会话相关的问题。
			HttpSession session = request.getSession();// Returns the current session associated 
			//with this request, or if the request does not have a session, creates one. 
			session.setAttribute("user", "xzj");
			//setting session to expire in 30 mins
			session.setMaxInactiveInterval(30*60);
			
			Cookie userName = new Cookie("user", user);
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


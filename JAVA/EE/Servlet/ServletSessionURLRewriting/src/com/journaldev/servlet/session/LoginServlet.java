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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private final String userID = "admin";
	private final String password = "password";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if(userID.equals(user) && password.equals(pwd)){
			HttpSession session = request.getSession();
			session.setAttribute("user", "xzj");
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			
			Cookie userName = new Cookie("user", user);
			response.addCookie(userName);
			
			// 我们可以使用HttpServletResponse encodeURL(）方法对URL进行编码，
			// 如果我们必须将请求重定向到另一个资源并且我们想要提供会话信息，
			// 我们可以使用encodeRedirectURL(）方法。
			String encodedURL = response.encodeRedirectURL("LoginSuccess.jsp");
			log("----------- encodedURL: " + encodedURL); // LoginSuccess.jsp;jsessionid=DDBB069A5B0CB7FE66F50B28637D8AFC
			// 1. encodeRedirectURL: 对指定的URL进行编码以在sendRedirect方法中使用，
			// 2.   或者，如果不需要编码，则返回URL不变。
			// 3.   该方法的实现包括`确定`是否需要在URL中编码会话ID的逻辑
			// 4.   由于进行此`确定`的规则可能与用于`决定`是否编码普通链接的规则不同，因此此方法与encodeURL方法分开。
			// 5. 编码出的url都应该通过HttpServletResponse.sendRedirect方法运行.
			// 否则，URL重写不能用于不支持cookie的浏览器。
			
			response.sendRedirect(encodedURL);
			// 使用指定的重定向位置URL向客户端发送临时重定向响应并清除缓冲区。 
			// 缓冲区将替换为此方法设置的数据。 调用此方法将状态代码设置为SC_FOUND 302(Found）。
			// 此方法可以接受相对URL;在将响应发送到客户端之前，servlet容器必须将相对URL转换为绝对URL。
			// 如果位置是相对的而没有前导'/'，则容器将其解释为相对于当前请求URI。 如果该位置与前导'/'相对，
			// 则容器将其解释为相对于servlet容器根。

			// 如果响应已提交，则此方法将抛出IllegalStateException。 使用此方法后，应认为响应已提交，不应写入。
			
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}

	}

}

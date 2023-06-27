package com.session.cookieDisabled;

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

// 在浏览器中禁用cookie来测试这个项目
// 如果未禁用cookie，则不会在URL中看到jsessionid
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		if("admin".equals(user) && "password".equals(pwd)){

			HttpSession session = request.getSession();
			session.setAttribute("user", "xzj");
			session.setMaxInactiveInterval(30*60);
			
			Cookie userName = new Cookie("user", user);
			response.addCookie(userName);

			// url-rewriting的流程:
			// 1. client禁用cookie
			// 2. client访问login.html,然后提交账号和密码
			// 3. 使用HttpServletResponse.encodeRedirectURL()对要 重定向 到的URL进行编码
			// 得到结果: LoginSuccess.jsp;jsessionid=DDBB069A5B0CB7FE66F50B28637D8AFC
			// 我们可以使用）方法。
			// 4. client接受到304状态码,然后重定向到LoginSuccess.jsp.这是关键的一步,因为client禁用了cookie
			// 所以client只能携带jsessionid来访问server: LoginSuccess.jsp;jsessionid=DDBB069A5B0CB7FE66F50B28637D8AFC
			// 假如client没有禁用cookie,url就不会携带jsessionid
			// 5.  LoginSuccess.jsp检测到了client禁用了cookie,所以从url的jsessionid中拿到session的id来识别client
			// 6. LoginSuccess.jsp把自己jsp页面中的url链接也要重新编码: response.encodeURL()
			String encodedURL = response.encodeRedirectURL("LoginSuccess.jsp");
			System.out.println("----------- encodedURL: " + encodedURL);
			// 5. 编码后的url应该通过HttpServletResponse.sendRedirect方法运行.
			// 否则，URL重写不能用于 不支持cookie 的浏览器
			response.sendRedirect(encodedURL);
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
			// rd.forward(request, response); // 原生的转发
		}
	}
}

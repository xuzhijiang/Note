package com.journaldev.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 一个简单的servlet，它将演示在具有特定路径的
// 在SetCookie中设置的cookie不会被浏览器发送到此servlet。
@WebServlet("/cookie/GetCookie")
public class GetCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// 返回一个client发送的伴随这个request的，包含所有cookie对象的数组。
		// 如果没有发送cookie，则此方法返回null。
		Cookie[] requestCookies = request.getCookies();
		
		out.write("<html><head></head><body>");
		out.write("<h3>Hello Browser!!</h3>");
		if(requestCookies != null){
			// print cookies from client
			out.write("<h3>Request Cookies:</h3>");
			for(Cookie c : requestCookies){
				out.write("Name="+c.getName()+", Value="+c.getValue()+", Comment="+c.getComment()
						+", Domain="+c.getDomain()+", MaxAge="+c.getMaxAge()+", Path="+c.getPath()
						+", Version="+c.getVersion());
				out.write("<br>");
				//delete cookie
				if(c.getName().equals("Test")){
					// GetCookie将“测试”cookie的MaxAge设置为0，以便客户端浏览器将其过期并删除
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
		out.write("</body></html>");
	}

}

package com.boot.servlet.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/cookie/GetCookie")
public class ServletGetCookie extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		// 如果client没有携带cookie，则此返回null。
		Cookie[] requestCookies = request.getCookies();
		
		out.write("<html><head></head><body>");
		out.write("<h3>Hello Browser!!</h3>");
		System.out.println("requestCookies: " + Arrays.toString(requestCookies));

		if(requestCookies != null){
			out.write("<h3>Request Cookies:</h3>");
			for(Cookie c : requestCookies){
				out.write("Name="+c.getName()+", Value="+c.getValue()+", Comment="+c.getComment()
						+", Domain="+c.getDomain()+", MaxAge="+c.getMaxAge()+", Path="+c.getPath()
						+", Version="+c.getVersion());
				out.write("<br>");

				if(c.getName().equals("Test")){
					// GetCookie将“测试”cookie的MaxAge设置为0，以便客户端浏览器将其删除
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}

		out.write("</body></html>");
	}

}

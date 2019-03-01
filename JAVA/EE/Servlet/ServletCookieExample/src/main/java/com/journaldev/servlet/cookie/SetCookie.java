package com.journaldev.servlet.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Cookie“Counter”仅发送到SetCookie，GetCookie将永远不会收到此Cookie。
// 除名称和值外，所有其他变量都是打印默认值。 MaxAge默认值为-1，版本默认值为0。
@WebServlet("/cookie/SetCookie")
public class SetCookie extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static int count = 0;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.write("<html><head></head><body>");
		out.write("<h3>Hello Browser!! xzj</h3>");
		
		// 获取浏览器携带的Cookie
		Cookie[] cookies = request.getCookies();
		System.out.println("SetCookie cookies: " + cookies);

		if(cookies != null){
			out.write("<h3>Request Cookies:</h3>");
			for(Cookie c : cookies){
				out.write("Name="+c.getName()+", Value="+c.getValue()+", Comment="+c.getComment()
						+", Domain="+c.getDomain()+", MaxAge="+c.getMaxAge()+", Path="+c.getPath()
						+", Version="+c.getVersion());
				out.write("<br>");
			}
		}
		
		// 我们将为每个cookie设置一个domain以及路径。
		// 以便其他servlet不会从客户端收到此cookie。
		
		//Set cookies for counter, accessible to only this servlet
		count++;
		Cookie counterCookie = new Cookie("Counter", String.valueOf(count));
		//add some description to be viewed in browser cookie viewer
		counterCookie.setComment("SetCookie Counter");
		//setting max age to be 1 day
		counterCookie.setMaxAge(24*60*60);
		//set path to make it accessible to only this servlet
		counterCookie.setPath("/ServletCookie/cookie/SetCookie");
		//adding cookie to the response
		response.addCookie(counterCookie);
		
		// 设置一个指定domain的cookie
		Cookie domainCookie = new Cookie("Test", "TestCookie"+String.valueOf(count));
		domainCookie.setComment("Test Cookie");
		response.addCookie(domainCookie);
		
		out.write("</body></html>");
	}

}

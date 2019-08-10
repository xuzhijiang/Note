package com.journaldev.servlet.cookie;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");

    	Cookie[] cookies = request.getCookies();
    	System.out.println("LogoutServlet cookies: " + Arrays.toString(cookies));

		Cookie loginCookie = null;
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("user")){
    			loginCookie = cookie;
    			break;
    		}
    	}

    	if(loginCookie != null){
			// 当我们点击Logout按钮时，我们应确保从客户端浏览器中删除cookie。
			loginCookie.setMaxAge(0);
        	response.addCookie(loginCookie);
    	}

    	response.sendRedirect("login.html");
    }

}

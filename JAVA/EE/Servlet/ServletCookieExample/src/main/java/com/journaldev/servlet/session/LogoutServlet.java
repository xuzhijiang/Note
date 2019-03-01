package com.journaldev.servlet.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");

    	Cookie[] cookies = request.getCookies();
    	System.out.println("LogoutServlet cookies: " + cookies);

		Cookie loginCookie = null;
    	for(Cookie cookie : cookies){
    		if(cookie.getName().equals("user")){
    			loginCookie = cookie;
    			break;
    		}
    	}

    	if(loginCookie != null){
    		loginCookie.setMaxAge(0);
        	response.addCookie(loginCookie);
    	}
    	response.sendRedirect("login.html");
    }

}

package com.session.cookieDisabled;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
    	Cookie[] cookies = request.getCookies();
    	if(cookies != null){
			for(Cookie cookie : cookies){
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
    	}
		// (LoginSuccess.jsp;jsessionid=DDBB069A5B0CB7FE66F50B28637D8AFC，)
    	// 如果浏览器禁用cookie，那么服务器是根据URL重写后的jsessionid来识别客户端的.
    	HttpSession session = request.getSession(false);
		if(session != null){
    		session.invalidate();
    	}

    	// no encoding because we have invalidated the session
    	response.sendRedirect("login.html");
    }

}

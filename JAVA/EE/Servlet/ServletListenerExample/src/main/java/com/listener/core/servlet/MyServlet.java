package com.listener.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/test-listener")
public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ServletContext servletContext = request.getServletContext();
			// ServletContext add attribute
			servletContext.setAttribute("User", "xzj");
			String user = (String) servletContext.getAttribute("User");
			// remove attr
			servletContext.removeAttribute("User");
			// create session
			HttpSession session = request.getSession();
			// destroy session
			session.invalidate();
			
			PrintWriter out = response.getWriter();
			out.write("Hi "+user);
	}

}

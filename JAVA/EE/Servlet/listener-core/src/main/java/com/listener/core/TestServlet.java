package com.listener.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/listener/hello")
public class TestServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			// MyServletContextAttributeListener测试
			ServletContext servletContext = request.getServletContext();
			servletContext.setAttribute("user", "xzj");
			try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace();}
			servletContext.setAttribute("user", "zs");
			try { Thread.sleep(3000L); } catch (InterruptedException e) { e.printStackTrace();}
			servletContext.removeAttribute("user");

			HttpSession session = request.getSession();
			session.invalidate();
			response.getWriter().write("hi hi hi!!!");
	}

}

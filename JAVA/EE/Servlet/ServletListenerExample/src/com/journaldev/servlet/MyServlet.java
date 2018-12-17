package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// MyServlet：一个简单的servlet类，我将在其中处理会话，属性等。

// Now when we will deploy our application and access MyServlet in browser 
// with URL http://localhost:8080/ServletListenerExample/MyServlet, 
// we will see logs in the server log file.

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			ServletContext ctx = request.getServletContext();
			ctx.setAttribute("User", "xzj");
			String user = (String) ctx.getAttribute("User");
			ctx.removeAttribute("User");
			
			HttpSession session = request.getSession();
			session.invalidate();
			
			PrintWriter out = response.getWriter();
			out.write("Hi "+user);
	}

}

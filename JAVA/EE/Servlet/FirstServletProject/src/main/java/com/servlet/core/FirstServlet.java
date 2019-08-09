package com.servlet.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 1. 在Servlet 3之前，我们需要在web.xml中配置Servlet,但servlet 3.0可以使用注解代替.
// 2. 也可以直接访问: hello.jsp, 因为hello.jsp不是私有的,可以自由访问.
// 3. @WebServlet注解的value是urlPatterns的别名.
@WebServlet(urlPatterns = {"/first-servlet-url-pattern"}, loadOnStartup = 1)
public class FirstServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String HTML_START="<html><body>";
	public static final String HTML_END="</body></html>";

    public FirstServlet() {
        super();
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("Served at ContextPath: " + request.getContextPath());
		PrintWriter out = response.getWriter();
		Date date = new Date();
		out.println(HTML_START + "<h2>FirstServlet doGet called!</h2><br/><h3>Date="+date +"</h3>"+HTML_END);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
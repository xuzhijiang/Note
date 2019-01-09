package com.javadev.servlet.FirstServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 在Servlet 3之前，我们需要在Web应用程序deployment descriptor中提供url pattern 信息，
// 但servlet 3.0使用易于理解的Java注释，并且错误的可能性较小。

// 访问: http://localhost:8080/FirstServlet/jsps/hello.jsp

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String HTML_START="<html><body>";
	public static final String HTML_END="</body></html>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		Date date = new Date();
		out.println(HTML_START + "<h2>Hi There!</h2><br/><h3>Date="+date +"</h3>"+HTML_END);
	}
	// 因此，servlet用于生成HTML并将其作为响应发送，如果您将查看doGet（）实现，
	// 我们实际上是在创建HTML文档时将其写入响应PrintWriter对象中，并且我们在需要它的地方添加动态信息。
		
	// 但如果响应很大，有很多动态数据，那么它很容易出错并且难以阅读和维护。 这是引入JSP的主要原因。
	// JSP is also server side technology.
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

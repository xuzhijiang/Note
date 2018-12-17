package com.journaldev.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 这是我们用于验证用户凭据的最终Servlet类，
// 注意使用Servlet配置和ServletConfig init参数的注释。

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Login Servlet", urlPatterns = {"/LoginServlet"},
		initParams = {
				@WebInitParam(name = "user", value = "xzj"), 
				@WebInitParam(name = "password", value = "password")
		})
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		
		//we can create DB connection resource here and set it to Servlet context
		
		// getServletContext得到的是ServletContext对象，ServletContext对象和web.xml关联，
		// 通过getInitParameter读取的参数就是web.xml里面的.
		
		if(getServletContext().getInitParameter("dbURL").equals("jdbc:mysql://localhost/mysql_db") &&
				getServletContext().getInitParameter("dbUser").equals("mysql_user") &&
				getServletContext().getInitParameter("dbUserPwd").equals("mysql_pwd"))
		{
			//将对象绑定到此ServletContext中的给定属性名称。 如果指定的名称已用于属性，则此方法将使用新属性替换该属性。

			//如果在ServletContext上配置了侦听器，则容器会相应地通知它们。

			//如果传递空值，则效果与调用removeAttribute（）相同。

			// 属性名称应遵循与包名称相同的约定。 Java Servlet API规范保留了与java.*，javax.*和sun.*匹配的名称。
			getServletContext().setAttribute("DB_Success", "True");
		} else {
			throw new ServletException("DB Connection error");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		
//		ServletConfig Interface
//
//		javax.servlet.ServletConfig用于将配置信息传递给Servlet。 
//		每个servlet都有自己的ServletConfig对象，servlet容器负责实例化这个对象。
//
//		我们可以在web.xml文件中提供servlet init参数, 或通过使用
//		WebInitParam注释提供servlet init参数
//
//		我们可以使用getServletConfig（）方法来获取初始化此servlet的ServletConfig对象。
//
//		public interface ServletConfig {
//
//			// The important methods of ServletConfig interface are:
//			
//			/**
//			此方法返回servlet的ServletContext对象。 我们将在下一节中研究ServletContext接口。
//			*/
//			public abstract ServletContext getServletContext();
//			
//			/**
//			此方法返回为servlet定义的init参数名称的Enumeration <String>。 如果没有定义init参数，
//			则此方法返回空枚举。
//			*/
//			public abstract Enumeration<String> getInitParameterNames() 
//
//			/**
//			此方法可用于按名称获取特定的init参数值。 如果the initialization parameter does not exist，则返回null。
//			*/
//			public abstract String getInitParameter(String paramString)
//		}
		
		//get servlet config init params
		// getServletConfig()返回 初始化此servlet的ServletConfig对象
		// getInitParameter() Gets the value of the initialization 
		// parameter with the given name.获取具有给定名称的初始化参数的值。
		String userID = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		
		//logging example
		log("User="+user+"::password="+pwd);
		
		if(userID.equals(user) && password.equals(pwd)){
			response.sendRedirect("LoginSuccess.jsp");
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}
		
	}

}

package com.journaldev.servlet.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyExceptionServlet")
public class MyExceptionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException("GET method is not supported.");
	}

}

// 由于浏览器只了解HTML，当我们的应用程序抛出异常时，servlet容器处理异常并生成HTML响应。
// 此逻辑特定于servlet容器。我正在使用tomcat并获取此错误页面(特定于tomcat服务器的错误页面)。
// 如果您将使用其他一些服务器，如JBoss或Glassfish，您可能会得到不同的错误HTML响应。

// 这种反应的问题在于它对用户没有价值。此外，它向用户显示我们的应用程序类和服务器详细信息对用户没有意义，从安全角度来看它并不好。

// 我确定您在尝试查找不存在的URL时必须看到404错误。让我们看看我们的servlet容器如何响应404错误。
// 如果我们发送无效网址请求，我们会收到如下图片的响应HTML。

// 同样，它是代表我们的应用程序由服务器生成的通用HTML，对用户几乎没有任何价值。

// Servlet API为我们可以在deploy descriptor中(web.xml)配置的自定义Exception和Error Handler servlet提供支持。
// 这些servlet的全部目的是处理应用程序引发的异常或错误，并向用户发送有用的HTML响应。
// 我们可以提供应用程序主页的链接或一些细节，让用户知道出了什么问题。


package com.journaldev.servlet.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

// 用于日志记录和会话验证的Servlet过滤器示例

// 在我们的servlet过滤器示例中，我们将创建过滤器来记录请求cookie和参数，log request cookies and parameters
// 并验证除static HTML和LoginServlet之外的所有资源的session，因为它没有会话。
/**
 * Servlet Filter implementation class RequestLoggingFilter
 */
@WebFilter("/RequestLoggingFilter")
public class RequestLoggingFilter implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("RequestLoggingFilter initialized");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()){
			String name = params.nextElement();
			String value = request.getParameter(name);
			this.context.log(req.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");
		}
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				// In server log file，您可以看到servlet filters和servlets 打印的日志。
				this.context.log(req.getRemoteAddr() + "::Cookie::{"+cookie.getName()+","+cookie.getValue()+"}");
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public void destroy() {
		//we can close resources here
	}

}

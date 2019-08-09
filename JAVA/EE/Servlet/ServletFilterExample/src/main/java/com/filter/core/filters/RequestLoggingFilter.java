package com.filter.core.filters;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  此过滤器用来记录请求cookie和参数，
 *
 * 注意:
 * 1. 容器首先处理url-patterns，然后处理servletNames，因此如果必须确保以特定顺序执行过滤器，请在定义过滤器映射时给予额外注意
 * 2. DispatcherType的类型: REQUEST，FORWARD，INCLUDE，ERROR和ASYNC,如果未定义dispatcher，
 * 则仅应用于客户端请求REQUEST
 */
// @WebFilter(value = "/*", servletNames = {"如果servlet的名字在这个数组中,就要经过此过滤器"})
@WebFilter(value = "/*")
public class RequestLoggingFilter implements Filter {

	private ServletContext context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		System.out.println("RequestLoggingFilter initialized");
		this.context.log("RequestLoggingFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("RequestLoggingFilter doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration<String> params = req.getParameterNames();
		
		while(params.hasMoreElements()){
			String name = params.nextElement();
			String value = request.getParameter(name);
			System.out.println(req.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");
		}
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				// In server log file，您可以看到servlet filters和servlets 打印的日志。
				System.out.println(req.getRemoteAddr() + "::Cookie::{"+cookie.getName()+","+cookie.getValue()+"}");
			}
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		//we can close resources here
	}

}

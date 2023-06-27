package com.filter.core.filters;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *  此过滤器用来记录请求中的cookie和参数，
 *
 * 1. 容器首先处理url-patterns，然后处理servletNames.
 * 2. DispatcherType的类型: REQUEST，FORWARD，INCLUDE，ERROR和ASYNC, 如果未定义dispatcher，则仅应用于过滤客户端的请求:REQUEST
 */
// @WebFilter(value = "/*", servletNames = {"如果servlet的名字在这个数组中,就要经过此过滤器"})
@WebFilter(urlPatterns = "/*")
public class RequestLoggingFilter implements Filter {

	@Override
	public void init(FilterConfig fConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("RequestLoggingFilter进行过滤******************");
		HttpServletRequest req = (HttpServletRequest) request;

		// 打印请求的参数
		Enumeration<String> params = req.getParameterNames();
		while(params.hasMoreElements()){
			String name = params.nextElement();
			String value = request.getParameter(name);
			System.out.println(req.getRemoteAddr() + "::Request Params::{"+name+"="+value+"}");
		}

		// 打印请求中的cookie
		Cookie[] cookies = req.getCookies();
		if(cookies != null){
			for(Cookie cookie : cookies){
				System.out.println(req.getRemoteAddr() + "::Cookie::{"+cookie.getName()+","+cookie.getValue()+"}");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}

}

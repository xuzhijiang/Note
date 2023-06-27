package com.filter.core.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// 验证用户是否有权限访问的 过滤器
public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {System.out.println("AuthenticationFilter初始化***********"); }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("*********AuthenticationFilter进行过滤");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		System.out.println("本地请求的uri: "+uri);
		HttpSession session = req.getSession(false);
		// 如果uri是登录页面,就不要重定向了,否则会造成用户无法登录,一直死循环重定向到
		if (session == null && !uri.endsWith("login") && !uri.endsWith("login.html")) {
			res.sendRedirect("login.html");
		} else {
			// 沿着过滤器链传递request
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {}
}

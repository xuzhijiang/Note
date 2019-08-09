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

// 用于验证用户是否有权限访问的过滤器
public class AuthenticationFilter implements Filter {

	private ServletContext context;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		System.out.println("AuthenticationFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("AuthenticationFilter doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		System.out.println("Requested Resource::"+uri);

		// 注意req.getSession()的参数
		HttpSession session = req.getSession(false);

		// url是登录页面,就不要重定向了,否则会造成用户一直在登录页面,无法登录的死循环
		if (session == null && !uri.endsWith("login") && !uri.endsWith("filter-welcome.html")) {
			System.out.println("Unauthorized access request");
			res.sendRedirect("filter-welcome.html");
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		//close any resources here
	}

}

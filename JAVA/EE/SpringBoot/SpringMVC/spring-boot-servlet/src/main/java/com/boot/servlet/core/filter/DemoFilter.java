package com.boot.servlet.core.filter;

import javax.servlet.*;
import java.io.IOException;

public class DemoFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("DemoFilter过滤器初始化: init called!!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("DemoFilter过滤器拦截请求=========>>>>>>: ");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}

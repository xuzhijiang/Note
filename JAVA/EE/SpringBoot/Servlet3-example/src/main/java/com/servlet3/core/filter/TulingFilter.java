package com.servlet3.core.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(value = "/tulingHello")
public class TulingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TulingFilter----->容器启动----------->");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("TulingFilter----->doFilter----------->");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
        System.out.println("TulingFilter----->destroy----------->");
    }
}

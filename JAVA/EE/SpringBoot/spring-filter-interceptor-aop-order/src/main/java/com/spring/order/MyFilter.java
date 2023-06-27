package com.spring.order;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("this is filter init....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter...start");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("doFilter...end");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }
}

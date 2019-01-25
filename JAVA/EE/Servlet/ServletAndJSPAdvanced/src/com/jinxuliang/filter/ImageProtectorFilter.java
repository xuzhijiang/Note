package com.jinxuliang.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ImageProtetorFilter", asyncSupported = true, urlPatterns = {"*.png", "*.jpg", "*.gif" })
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("ImageProtectorFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String referrer = httpServletRequest.getHeader("referer");
                System.out.println("referrer:" + referrer);
        if (referrer != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("Image not available" );
        }
    }

    @Override
    public void destroy() {

    }
}

package com.jinxuliang.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 限制图片仅允许自己网页引用(即实现图片的防盗链功能）

// access: http:localhost:8080/image/test.jpg(尝试直接访问则报错……)
// http:localhost:8080/showImages.jsp(通过网页访问则可以)
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

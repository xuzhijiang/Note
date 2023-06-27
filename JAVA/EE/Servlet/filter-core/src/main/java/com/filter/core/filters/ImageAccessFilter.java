package com.filter.core.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// 实现图片的防盗链功能,限制图片仅允许自己网页引用

// 效果:
// 访问: http:localhost:8080/image/test.jpg 会直接报错
// 通过 http:localhost:8080/showImages.jsp 访问则可以
@WebFilter(urlPatterns = {"*.png", "*.jpg", "*.gif" })
public class ImageAccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String referrer = httpServletRequest.getHeader("referer");
        System.out.println("ImageAccessFilter referrer******: " + referrer);
        if (referrer != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new ServletException("Image not available" );
        }
    }

    @Override
    public void destroy() {}
}

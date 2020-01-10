package com.spring.mvc.core.interceptor;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 记录处理请求所花费的总时间(包括渲染视图页面)
public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        long startTime = System.currentTimeMillis();
        System.out.println("请求的url: " + request.getRequestURL().toString() + "\t 开始时间: " + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("请求的url:" + request.getRequestURL().toString()  + "\t Sent to Handler 当前时间为: " + System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        System.out.println("请求的url:" + request.getRequestURL().toString() + "\t 总耗时: " + (System.currentTimeMillis() - startTime));
    }

}

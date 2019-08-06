package com.springboot.mvc.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Spring MVC提供了一个HandlerInterceptor接口，这一接口中方法，会在SpringMVC响应HTTP请求的特定阶段被调用。

// 我们可以利用这一特性完成一些特殊的任务，比如统计特定方法的调用次数，方法的执行时间等>

/**
 * 统计方法的执行时间
 */
public class WebStopWatch implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("开始统计本次请求的时间");
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("本次请求处理时间： "+useTime+" ms");
    }

}

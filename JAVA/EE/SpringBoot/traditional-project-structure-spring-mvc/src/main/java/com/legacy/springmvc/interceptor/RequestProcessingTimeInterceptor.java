package com.legacy.springmvc.interceptor;


import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 像Struts2拦截器一样，可以通过:
// 1. 实现org.springframework.web.servlet.HandlerInterceptor接口
// 2. 或实现抽象类org.springframework.web.servlet.handler.HandlerInterceptorAdapter
// 来创建Spring拦截器，它提供了HandlerInterceptor接口的基本实现。

/**
 *  为简单起见，扩展抽象类HandlerInterceptorAdapter。
 *  HandlerInterceptorAdapter是HandlerInterceptor接口的抽象适配器类，
 *
 *  逻辑非常简单，只是记录处理请求所花费的总时间(包括渲染视图页面)
 */
public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {

    /**
     * 此方法在将请求转发给"对应的处理方法-handler method"之前拦截请求。
     *
     * a. 这个方法应该返回'true'让Spring知道要让下一个Spring拦截器处理请求，
     * 或者如果没有其他Interceptor则将它发送到处理程序方法。
     * b. 如果此方法返回'false'，则Spring框架假定请求已由spring拦截器本身处理，
     * 并且不需要进一步处理。在这种情况下，我们应该使用"response"来给客户端的请求发送响应
     *
     * 此方法也可以抛出异常，在这种情况下，Spring MVC异常处理应该很有用,它会将错误页面作为响应发送。
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * a. 当已经调用处理程序,但DispatcherServlet尚未渲染视图时(is yet to render the view)，
     * 此时将调用HandlerInterceptor拦截器方法。此方法可用于向要在"视图页面"中使用的ModelAndView对象添加其他属性。
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

}

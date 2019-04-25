package com.journaldev.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 像Struts2拦截器一样，可以通过
// 实现org.springframework.web.servlet.HandlerInterceptor接口
//或实现抽象类org.springframework.web.servlet.handler.HandlerInterceptorAdapter
//来创建Spring拦截器，它提供了HandlerInterceptor接口的基本实现。

// 如果配置了多个spring interceptors，则按配置顺序执行preHandle(）方法，
// 而以相反顺序(in the reverse order)调用postHandle(）和afterCompletion(）方法。

// 为简单起见，扩展抽象类HandlerInterceptorAdapter。 
// HandlerInterceptorAdapter是HandlerInterceptor接口的抽象适配器类，
// 用于简化pre-only/post-only拦截器的实现。

// 逻辑非常简单，我只是记录处理程序方法执行的时序和处理请求所花费的总时间(包括渲染视图页面)
// The logic is really simple, I am just logging the timings of
// handler method execution and total time taken in processing
// the request including rendering view page.
public class RequestProcessingTimeInterceptor extends HandlerInterceptorAdapter {

    // Spring HandlerInterceptor根据我们想在哪里拦截HTTP请求声明三种方法：

    private static final Logger logger = LoggerFactory
            .getLogger(RequestProcessingTimeInterceptor.class);

    /**
     * 此方法用于在请求移交给处理程序方法(handler method)之前拦截请求。
     * <p>
     * a. 这个方法应该返回'true'让Spring知道通过另一个Spring拦截器处理请求，
     * 或者如果没有其他Interceptor则将它发送到处理程序方法。
     * <p>
     * b. 如果此方法返回'false'，则Spring框架假定请求已由spring拦截器本身处理，
     * 并且不需要进一步处理。
     * 在这种情况下，我们应该使用"响应对象"来给客户端请求发送响应。
     * We should use response object to
     * send response to the client request in this case.
     * <p>
     * Object handler是处理请求(handle the request)的选定处理程序对象(handler object )。
     *
     * 此方法也可以抛出异常，在这种情况下，Spring MVC异常处理应该很有用,
     * 它会将错误页面作为响应发送。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: Start Time=" + System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        return true;
    }

    /**
     * a. 当HandlerAdapter已经调用the handler(处理程序)但DispatcherServlet
     * 尚未渲染视图时(is yet to render the view)，此时将调用HandlerInterceptor拦截器方法。
     * 此方法可用于向要在"视图页面"中使用的ModelAndView对象添加其他属性。
     * <p>
     * b. 我们可以使用这个spring拦截器方法来确定处理程序方法处理客户端
     * 请求所花费的时间(the time taken
     * by handler method to process the client request)。
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("Request URL::" + request.getRequestURL().toString()
                + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
    }

    /**
     * a. 这是一个HandlerInterceptor回调方法，在执行处理程序(the handler is executed,
     * and view is rendered)并渲染视图时调用一次该方法。
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: End Time=" + System.currentTimeMillis());
        logger.info("Request URL::" + request.getRequestURL().toString()
                + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
    }

}

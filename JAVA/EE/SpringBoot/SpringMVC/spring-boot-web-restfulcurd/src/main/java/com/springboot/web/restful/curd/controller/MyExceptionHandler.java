package com.springboot.web.restful.curd.controller;

import com.springboot.web.restful.curd.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

// 异常处理器,专门用于处理异常.
@ControllerAdvice // 在springmvc中要成为异常处理器,必须要添加这个注解.
public class MyExceptionHandler {

    // 1. 如果这样定义的话,无论是浏览器访问,还是其他client访问,返回的都是json
//    @ResponseBody
//    @ExceptionHandler(UserNotExistException.class)
//    public Map<String, Object> handleException(Exception e) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", "user not exist");
//        map.put("message", e.getMessage());
//        return map;
//    }

    // 2. 自适应的响应,根据client是浏览器还是其他客户端 返回不同形式的数据
    @ExceptionHandler(UserNotExistException.class) // 用这个注解标注 要处理的是什么异常.
    public String handleException(Exception e, HttpServletRequest request) { // 只要出现了UserNotExistException异常,springmvc会调用这个方法,把异常传进来
        // 自定义自己想要返回的json错误信息
        Map<String,Object> map = new HashMap<>();
        //传入我们自己的错误状态码  4xx 5xx(如果不传的话,默认就是200了)
        /**
         * Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
         */
        request.setAttribute("javax.servlet.error.status_code", 500);
        map.put("code", "user.notexist");
        map.put("message", "用户出错啦");

        // 把ext放在request域中.然后MyErrorAttributes会去读取.
        request.setAttribute("ext", map);

        // 转发到/error,BasicErrorController会对/error进行
        // 自适应处理(所谓自适应处理就是: 浏览器和非浏览器返回的效果可以自适应,浏览器返回错误提示页面,其他非浏览器返回自定义的json数据)
        return "forward:/error";
    }

    // 状态吗为200,返回json
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Map<String, Object> handleIllegalArgumentException(HttpServletRequest request, Exception ex){
        Map<String, Object> map = new HashMap<>();
        map.put("url", request.getRequestURL().toString());
        map.put("message", ex.getMessage());
        return map;
    }
}

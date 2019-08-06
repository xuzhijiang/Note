package com.springboot.mvc.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

// 有时我们需要设置一些“影响全局”的配置，这时，可以使用@ControllerAdivice完成这个工作
@ControllerAdvice
public class MyControllerAdvice {

    // 使用@ExceptionHandler将一个方法标识为“全局的出错处理程序”
    // 配置一个全局的出错处理程序，应用于所有控制器中的所有方法
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request){
        // 注意这里指定了一个视图模板——error,需要创建error.html
        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject("errorMessage",exception.getMessage());
        modelAndView.addObject("now",new Date().toString());
        return modelAndView;
    }
}

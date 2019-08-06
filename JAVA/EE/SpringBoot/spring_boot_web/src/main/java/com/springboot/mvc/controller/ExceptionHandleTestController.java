package com.springboot.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("exception")
public class ExceptionHandleTestController {

    // 测试全局异常处理器:
    // 在这个方法中主动抛出一个异常
    // 这个方法展示使用ControllerAdivce所定义的全局异常处理器的使用
    // 控制器的方法中出现了未捕获的异常，就会由@ControllerAdivice配
    // 置的全局异常处理器进行处理，显示一个异常处理页。
    @RequestMapping("/advice")
    public String useControllerAdvice() {
        throw new IllegalArgumentException("参数有误！");
    }
}

package com.spring.mvc.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterceptorController {
    @GetMapping("/interceptor")
    public String test01() {
        System.out.println("******目标方法 test01 执行*******");
        return "success_bad_code";
    }
}

package com.spring.mvc.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterceptorController {
    @GetMapping("/interceptor")
    public String method() {
        System.out.println("测试拦截器");
        return "success_bad_code";
    }
}

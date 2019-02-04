package com.jinxuliang.spring_advanced_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public String hello(){
        //使用放在resources/templates/hello.html作为模板
        //Thymeleaf生成HTML返回给客户端
        return "hello";
    }
}
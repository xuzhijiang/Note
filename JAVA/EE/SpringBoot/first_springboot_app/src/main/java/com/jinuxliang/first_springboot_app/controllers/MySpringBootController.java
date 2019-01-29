package com.jinuxliang.first_springboot_app.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标准的Spring Boot REST控制器
 */
@RestController
@RequestMapping("/spring")
public class MySpringBootController {
    @RequestMapping("/hello")
    public String hello(){
        return "Hello,Spring Boot!";
    }
}

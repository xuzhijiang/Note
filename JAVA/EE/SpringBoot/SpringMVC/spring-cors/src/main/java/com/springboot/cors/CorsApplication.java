package com.springboot.cors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// SpringBoot配置跨域的方式
// 1. 注解的方式: @CrossOrigin
// 2. java配置的方式
// 3. HttpServletResponse修改响应header
@SpringBootApplication
public class CorsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CorsApplication.class, args);
    }
}

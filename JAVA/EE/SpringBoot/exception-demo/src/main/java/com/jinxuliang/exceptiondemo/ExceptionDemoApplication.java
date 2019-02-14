package com.jinxuliang.exceptiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 本部分主要介绍了如何使用ControllerAdvice来拦截HTTP请求，
// 并针对特定异常类型进行专门的处理。配合默认的Spring Boot异常处理机制，基本上也够用了。
@SpringBootApplication
public class ExceptionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionDemoApplication.class, args);
    }
}

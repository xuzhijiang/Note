package com.springboot.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * spring mvc异常处理参考: https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 *
 * jsp + spring boot
 *
 * 默认情况下， Spring Boot项目中如果引入了spring-boot-starter-web依赖，则会启动一个嵌入式的tomcat，并且自动配置好DispatcherServlet，从而你就直接可以编写控制器了。
 *
 * 这里要注意的，是默认使用视图引擎并不是JSP，而是Thymeleaf，这么做的原因，在于在使用嵌入式的tomcat作为Server时,JSP有相应的限制,Spring Boot项目，不建议再使用JSP。
 */
@SpringBootApplication
public class ExceptionDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionDemoApplication.class, args);
    }
}

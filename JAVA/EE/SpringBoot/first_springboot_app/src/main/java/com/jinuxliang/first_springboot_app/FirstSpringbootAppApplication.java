package com.jinuxliang.first_springboot_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstSpringbootAppApplication {

    public static void main(String[] args) {
        // 调用了SpringApplication的静态方法run。
        // 这个run方法会构造一个SpringApplication的实例，
        // 然后再调用这里实例的run方法就表示启动SpringBoot

        // 想要分析SpringBoot的启动过程，我们需要熟悉SpringApplication
        // 的构造过程以及SpringApplication的run方法执行过程即可
        SpringApplication.run(FirstSpringbootAppApplication.class, args);

        // 大概流程:
        // 1. 创建AnnotationConfigServletWebServerApplicationContext
        // 2. 此AnnotationConfigServletWebServerApplicationContext实例用于
        // 创建Servlet容器工厂类的实例
        // 3. 这个Servlet容器工厂类的实例用于创建Servlet容器类的实例
        // 4. Servlet容器实例在默认端口8080处启动容器并部署我们的Spring Boot WebApplication

    }

}

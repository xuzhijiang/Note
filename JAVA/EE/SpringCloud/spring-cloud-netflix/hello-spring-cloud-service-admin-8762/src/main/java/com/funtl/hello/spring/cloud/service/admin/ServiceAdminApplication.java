package com.funtl.hello.spring.cloud.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// 创建服务提供者

/**
 * 启动工程，打开 http://localhost:8761 ，即 Eureka Server 的网址
 *
 * 你会发现一个服务已经注册在服务中了，服务名为 HELLO-SPRING-CLOUD-SERVICE-ADMIN ,端口为 8762
 *
 * 这时打开 http://localhost:8762/hi?message=HelloSpring ，你会在浏览器上看到 :
 *
 * Hi，your message is :"HelloSpring" i am from port：8762
 */
@SpringBootApplication
@EnableEurekaClient// 通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client.
public class ServiceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAdminApplication.class, args);
    }

}

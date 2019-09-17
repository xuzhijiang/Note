package com.funtl.hello.spring.cloud.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// 创建服务提供者
@SpringBootApplication
// 通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client.
// 说明在配置文件中,要把当前服务注册到eureka服务端,说明这是一个服务提供者.能够被其他服务消费者在eureka Server上发现.
@EnableEurekaClient
public class ServiceAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAdminApplication.class, args);
    }
}

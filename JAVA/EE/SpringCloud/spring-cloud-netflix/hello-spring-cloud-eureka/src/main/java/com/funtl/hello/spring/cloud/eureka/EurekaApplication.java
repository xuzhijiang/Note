package com.funtl.hello.spring.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 项目来源: https://www.funtl.com/zh/spring-cloud-netflix/

// Eureka Server 是有界面的，启动工程，打开浏览器访问：http://localhost:8761
@SpringBootApplication
// @EnableEurekaServer表明这是一个EurekaServer,
// 是一个服务注册中心
@EnableEurekaServer
public class EurekaApplication { // 注意包名和类名的命名规范

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}

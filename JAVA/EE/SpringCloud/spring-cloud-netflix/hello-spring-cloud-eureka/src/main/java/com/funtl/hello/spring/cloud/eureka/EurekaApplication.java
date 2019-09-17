package com.funtl.hello.spring.cloud.eureka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 服务注册与发现
@SpringBootApplication
@EnableEurekaServer// 启动一个服务注册中心，只需要一个注解 @EnableEurekaServer
public class EurekaApplication { // 注意包名和类名的命名规范

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }

    // Eureka Server 是有界面的，启动工程，打开浏览器访问：http://localhost:8761
}

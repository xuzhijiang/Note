package com.funtl.hello.spring.cloud.alibaba.nacos.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient// 通过 @EnableDiscoveryClient 注解表明是一个 Nacos 客户端，该注解是 Spring Cloud 提供的原生注解
public class NacosProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }

    @Value("${server.port}")
    private String port;

    @RestController
    public class EchoController {

        @GetMapping(value = "/echo/{message}")
        public String echo(@PathVariable String message) {
            return "Hello Nacos Discovery " + message + ", i am from port: " + port;
        }

    }
}

/**
 * 启动工程
 * 通过浏览器访问 http://localhost:8848/nacos，即 Nacos Server 网址
 *
 * 你会发现一个服务已经注册在服务中了，服务名为 nacos-provider
 * 这时打开 http://localhost:8081/echo/hi ，你会在浏览器上看到：
 * Hello Nacos Discovery hi
 *
 *
 * 服务的端点检查:
 * spring-cloud-starter-alibaba-nacos-discovery 在实现的时候提供了一个 EndPoint,
 * EndPoint 的访问地址为 http://ip:port/actuator/nacos-discovery(http://localhost:8081/actuator/nacos-discovery)。
 * EndPoint 的信息主要提供了两类:
 *
 * 1、subscribe: 显示了当前有哪些服务订阅者
 * 2、NacosDiscoveryProperties: 显示了当前服务实例关于 Nacos 的基础配置
 * 通过浏览器访问 http://localhost:8081/actuator/nacos-discovery 你会在浏览器上看到：
 * */
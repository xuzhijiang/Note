package com.funtl.hello.spring.cloud.web.admin.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 在spring cloud微服务架构中，服务消费者与服务提供者的通讯方式是基于 http restful 的。
 *
 * Spring cloud 有两种服务的调用方式，一种是 ribbon + restTemplate，另一种是 feign(内部帮我们实现了rest方式的调用.)
 * WebAdminRibbonApplication使用的是第一种方式: ribbon + rest
 */
@SpringBootApplication
// 通过 @EnableDiscoveryClient 发现在Eureka Server上的服务提供者.
// 同时也会将WebAdminRibbonApplication这个服务消费者注册到Eureka Server上.
@EnableDiscoveryClient
// Ribbon 中使用熔断器,增加 @EnableHystrix 注解
@EnableHystrix
// 增加 Hystrix 仪表盘功能,启用HystrixDashboard
@EnableHystrixDashboard
public class WebAdminRibbonApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebAdminRibbonApplication.class, args);
    }
}

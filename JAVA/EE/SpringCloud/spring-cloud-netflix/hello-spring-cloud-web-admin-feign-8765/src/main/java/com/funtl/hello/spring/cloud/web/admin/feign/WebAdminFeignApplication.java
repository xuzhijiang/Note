package com.funtl.hello.spring.cloud.web.admin.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign和ribbon都是服务消费者.
 * Feign 是一个声明式的 Http 客户端，它使得写 Http 客户端变得更简单。
 *
 * 使用 Feign，只需要创建一个接口并注解.
 *
 * Feign 默认集成了 Ribbon(Feign 整合了 ribbon)，默认实现了负载均衡的效果
 * 所以注意: 我们一般是使用Feign,而不是ribbon.
 */
@SpringBootApplication
// 通过 @EnableDiscoveryClient 启用服务发现功能.说明当前项目是服务消费者.
// 注意Feign和Ribbon都是服务消费者,所以不要添加@EnableEurekaClient
// @EnableDiscoveryClient同时也会把WebAdminFeignApplication注册到Eureka上.
@EnableDiscoveryClient
// 通过 @EnableFeignClients 注解开启 Feign 功能
@EnableFeignClients
// 通过添加@EnableHystrixDashboard，增加 Hystrix 仪表盘功能,启用HystrixDashboard
@EnableHystrixDashboard
public class WebAdminFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminFeignApplication.class, args);
    }
}

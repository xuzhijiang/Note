package com.funtl.hello.spring.cloud.web.admin.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Feign 是一个声明式的伪 Http 客户端，它使得写 Http 客户端变得更简单。
 *
 * 使用 Feign，只需要创建一个接口并注解.
 *
 * Feign 默认集成了 Ribbon(Feign 整合了 ribbon)，并和 Eureka 结合，默认实现了负载均衡的效果
 * 所以注意: 我们一般是使用Feign,而不是ribbon.
 */
@SpringBootApplication
@EnableDiscoveryClient // 通过 @EnableDiscoveryClient 启用服务发现功能.说明当前项目是服务消费者.
@EnableFeignClients // 通过 @EnableFeignClients 注解开启 Feign 功能
@EnableHystrixDashboard // 通过添加@EnableHystrixDashboard，增加 Hystrix 仪表盘功能,启用HystrixDashboard
public class WebAdminFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminFeignApplication.class, args);
    }
}

/**
 * 测试访问: 在浏览器上多次访问 http://localhost:8765/hi?message=HelloFeign
 *
 * 浏览器交替显示：
 *
 * Hi，your message is :"HelloFeign" i am from port：8762
 * Hi，your message is :"HelloFeign" i am from port：8763
 *
 * 请求成功则表示我们已经成功实现了 Feign 功能来访问不同端口的实例
 */

/**
 * 测试熔断器
 *
 * 此时我们关闭服务提供者，再次请求 http://localhost:8765/hi?message=HelloFeign 浏览器会显示：
 *
 * Hi，your message is :"HelloFeign" but request error.
 *
 * */
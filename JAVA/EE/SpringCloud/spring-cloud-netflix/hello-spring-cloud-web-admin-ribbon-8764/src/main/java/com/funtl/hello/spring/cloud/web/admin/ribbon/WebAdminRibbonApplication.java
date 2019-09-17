package com.funtl.hello.spring.cloud.web.admin.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于 http restful 的。
 *
 * Spring cloud 有两种服务调用方式，一种是 ribbon + restTemplate，另一种是 feign。
 *
 * 这里是 ribbon + rest
 *
 * Ribbon 是一个负载均衡客户端，可以很好的控制 http 和 tcp 的一些行为。
 */
@SpringBootApplication
@EnableDiscoveryClient // 通过 @EnableDiscoveryClient 发现服务.
@EnableHystrix // Ribbon 中使用熔断器,增加 @EnableHystrix 注解
@EnableHystrixDashboard // 增加 Hystrix 仪表盘功能,启用HystrixDashboard
public class WebAdminRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAdminRibbonApplication.class, args);
    }

}

/**
 * 测试访问：在浏览器上多次访问 http://localhost:8764/hi?message=HelloRibbon
 *
 * 浏览器交替显示：
 *
 * Hi，your message is :"HelloRibbon" i am from port：8762
 * Hi，your message is :"HelloRibbon" i am from port：8763
 *
 * 请求成功则表示我们已经成功实现了负载均衡功能来访问不同端口的实例
 * 相当于我们请求的是8764这个服务实例,然后这个服务实例通过ribbon+rest，
 *  把请求调度到了hello-spring-cloud-service-admin服务实例上，因为有2个
 *  hello-spring-cloud-service-admin服务实例，所以会根据ribbon算法选择一个。
 *
 * 此时的架构(classpath:ribbon-architecture.png)：
 *
 * 1. 一个服务注册中心，Eureka Server，端口号为：8761
 * 2. service-admin 工程运行了两个实例，端口号分别为：8762，8763
 * 3. web-admin-ribbon 工程端口号为：8764
 * 4. web-admin-ribbon 通过 RestTemplate 调用 service-admin 接口时因为启用了
 * 负载均衡功能故会轮流调用它的 8762 和 8763 端口
 */

/**
 * 测试熔断器
 *
 * 此时我们关闭服务提供者，再次请求 http://localhost:8764/hi?message=HelloRibbon 浏览器会显示：
 *
 * Hi，your message is :"HelloRibbon" but request error.
 * */
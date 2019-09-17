package com.funtl.hello.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 依次运行 EurekaApplication、ServiceAdminApplication、WebAdminRibbonApplication、
 * WebAdminFeignApplication、ZuulApplication
 *
 * 打开浏览器访问：http://localhost:8769/api/a/hi?message=HelloZuul 浏览器显示
 * Hi，your message is :"HelloZuul" i am from port：8763
 *
 * 打开浏览器访问：http://localhost:8769/api/b/hi?message=HelloZuul 浏览器显示
 * Hi，your message is :"HelloZuul" i am from port：8763
 * 至此说明 Zuul 的路由功能配置成功
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy // 增加 @EnableZuulProxy 注解开启 Zuul 路由网关功能
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}

package com.funtl.hello.spring.cloud.alibaba.nacos.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients// 通过 @EnableFeignClients 注解开启 Feign 功能
public class NacosConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerFeignApplication.class, args);
    }

}

/**
 * 启动工程
 * 通过浏览器访问 http://localhost:8848/nacos，即 Nacos Server 网址
 * 你会发现多了一个名为 nacos-consumer-feign 的服务
 * 这时打开 http://localhost:9092/echo/hi ，你会在浏览器上看到：
 * Hello Nacos Discovery Hi Feign
 *
 *
 *
 * 负载均衡:
 * 在浏览器上多次访问 http://localhost:9092/echo/hi ，浏览器交替显示：
 * Hello Nacos Discovery Hi Feign i am from port 8081
 * Hello Nacos Discovery Hi Feign i am from port 8082
 *
 *
 * 测试熔断器:
 * 此时我们关闭服务提供者，再次请求 http://localhost:9092/echo/hi 浏览器会显示：
 * echo fallback
 *
 *
 *
 * 测试Sentinel控制台:
 *
 * 打开浏览器访问：http://localhost:8080/#/dashboard/home
 * 再Sentinel控制台，会看到，多了一个名为 nacos-consumer-feign 的服务
 */
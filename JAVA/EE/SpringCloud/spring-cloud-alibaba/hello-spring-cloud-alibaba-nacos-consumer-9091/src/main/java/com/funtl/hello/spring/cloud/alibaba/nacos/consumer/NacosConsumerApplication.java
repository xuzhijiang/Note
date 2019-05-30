package com.funtl.hello.spring.cloud.alibaba.nacos.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }
}

/**
 * 启动工程
 *
 * 通过浏览器访问 http://localhost:8848/nacos，即 Nacos Server 网址
 * 你会发现多了一个名为 nacos-consumer 的服务
 *
 * 这时打开 http://localhost:9091/echo/app/name ，你会在浏览器上看到：
 * Hello Nacos Discovery nacos-consumer
 *
 * 服务的端点检查
 *
 * 通过浏览器访问 http://localhost:9091/actuator/nacos-discovery 你会在浏览器上看到：
 *
 *  * 负载均衡:
 *  * 在浏览器上多次访问 http://localhost:9092/echo/hi ，浏览器交替显示：
 *  * Hello Nacos Discovery Hi Feign i am from port 8081
 *  * Hello Nacos Discovery Hi Feign i am from port 8082
 */
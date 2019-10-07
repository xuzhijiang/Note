package com.funtl.hello.spring.cloud.alibaba.nacos.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * http://localhost:8848/nacos
 * 你会发现一个服务已经注册在服务中了，服务名为 nacos-provider
 */
@SpringBootApplication
// 通过 @EnableDiscoveryClient 注解表明是一个 Nacos 客户端，该注解是 Spring Cloud 提供的原生注解(侵入性低)
@EnableDiscoveryClient
public class NacosProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication.class, args);
    }
}

package com.funtl.hello.spring.cloud.alibaba.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 访问 http://localhost:8848/nacos
 * 你会发现一个服务已经注册在服务中了，服务名为 nacos-provider
 *
 * spring-cloud-starter-alibaba-nacos-discovery 在实现的时候提供了一个 EndPoint端点,
 * 地址为 http://ip:port/actuator/nacos-discovery
 * (http://localhost:8081/actuator/nacos-discovery)。
 *
 * EndPoint 的信息主要提供了两类:
 * 1、subscribe: 显示了当前有哪些服务订阅者
 * 2、NacosDiscoveryProperties: 显示了当前服务实例关于 Nacos 的基础配置
 *
 * 健康检查原理:Nacos注册中心不断的和服务提供者发心跳检测,一旦发现没有响应了,就把这个服务从注册中心中删掉.
 * */
@RestController
public class NacosProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "echo/{message}")
    public String echo(@PathVariable(value = "message") String message) {
        return "Hello Nacos Discovery " + message + ", i am from port: " + port;
    }
}

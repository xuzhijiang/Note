package com.funtl.hello.spring.cloud.alibaba.nacos.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
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

    /**
     * 下面的是为了测试是否可以从远程Nacos Server拉取配置文件
     */

    // 注入配置文件上下文
//    @Autowired
//    private ConfigurableApplicationContext applicationContext;

    // 从上下文中读取配置
//    @GetMapping(value = "/hi")
//    public String sayHi() {
//        return "Hello " + applicationContext.getEnvironment().getProperty("username");
//    }
}

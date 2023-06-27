package com.itheima.security.distributed.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient // 网关也要从注册中心拿服务地址,所以需要这个注解
public class GatewayServer {
    // 以后就直接向网关发请求,不是像某个微服务发请求,gateway就会把token解析,转给微服务 .
    // 微服务就可以解析出token的权限,进行授权
    public static void main(String[] args) {
        SpringApplication.run(GatewayServer.class, args);
    }
}

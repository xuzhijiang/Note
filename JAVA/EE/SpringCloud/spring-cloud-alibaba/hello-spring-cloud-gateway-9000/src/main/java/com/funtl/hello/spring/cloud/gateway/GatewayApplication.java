package com.funtl.hello.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
/**
 * 测试访问
 * 依次运行 Nacos 服务、NacosProviderApplication、NacosConsumerApplication、NacosConsumerFeignApplication、GatewayApplication
 *
 * 打开浏览器访问：http://localhost:9000/nacos-consumer/echo/app/name 浏览器显示
 * Hello Nacos Discovery nacos-consumer i am from port 8082
 *
 * 打开浏览器访问：http://localhost:9000/nacos-consumer-feign/echo/hi 浏览器显示
 * Hello Nacos Discovery Hi Feign i am from port 8082
 *
 * 注意：请求方式是 http://路由网关IP:路由网关Port/服务名/**
 *
 * 至此说明 Spring Cloud Gateway 的路由功能配置成功
 */

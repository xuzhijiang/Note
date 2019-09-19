package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// 可以指定Feign的包名
// @EnableFeignClients(basePackages = {"com.luo.springcloud"})
public class DeptConsumer80_Feign_App {
    public static void main(String[] args){
        SpringApplication.run(DeptConsumer80_Feign_App.class, args);
    }
}

package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 测试Feign:
 *
 * 1. 启动3个Eureka集群
 * 2. 启动三个部门微服务提供者
 * 3. 启动Feign
 * 4. 访问http://localhost/consumer/dept/list即可
 *
 * 注意microservicecloud-consumer-dept-feign是在microservicecloud-consumer-dept-80基础上得来的,
 * 也是充当consumer的角色
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.luo.springcloud"})
@ComponentScan("com.luo.springcloud")
public class DeptConsumer80_Feign_App {

    public static void main(String[] args){
        SpringApplication.run(DeptConsumer80_Feign_App.class, args);
    }

}

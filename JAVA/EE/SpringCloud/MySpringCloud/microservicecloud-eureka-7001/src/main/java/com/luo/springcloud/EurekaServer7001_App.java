package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka集群测试:
 *
 * - 访问eureka7001.com:7001
 * - 访问eureka7002.com:7002
 * - 访问eureka7003.com:7003
 */
@SpringBootApplication
@EnableEurekaServer// EurekaServer服务器端启动类，接收其它微服务注册进来
public class EurekaServer7001_App {

    public static void main(String[] args){
        SpringApplication.run(EurekaServer7001_App.class, args);
    }

}

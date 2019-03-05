package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册与发现
 *
 * Eureka三大角色
 *
 * - Eureka Server提供服务注册和发现
 * - Service Provider服务提供方将自身服务注册到Eureka， 从而使服务的消费者能够找到
 * - Service Consumer服务消费方从Eureka获取注册服务列表，从而能够消费
 */

/**
 * 输入http://localhost:7001/，看到Spring Eureka界面表示成功，
 * 这个访问链接和程序中的application.yml配置吻合。
 *
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

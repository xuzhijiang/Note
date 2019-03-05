package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * ### 测试熔断机制
 *
 * 启动三个Eureka集群，启动服务主启动类DeptProvider8001_Hystrix_App，
 * 客户端启动microservicecloud-consumer-dept-80，页面访问http://localhost/consumer/dept/get/112
 */
@SpringBootApplication
@EnableEurekaClient // 本服务启动后会注册到Eureka服务注册中心
@EnableDiscoveryClient // 服务发现
@EnableCircuitBreaker  //对Hystrix熔断机制的支持, Circuit: 周,环形，巡回，路线，流程
public class DeptProvider8001_Hystrix_App {
	public static void main(String[] args) {
		SpringApplication.run(DeptProvider8001_Hystrix_App.class, args);
	}
}

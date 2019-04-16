package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 监控页面测试:
 *
 * 1. 启动: microservicecloud-consumer-hystrix-dashboard
 * 2. 访问: http://localhost:9001/hystrix
 *
 * 全部测试:
 *
 * 1. 启动3个Eureka集群
 * 2. 启动microservicecloud-provider-dept-hystrix-8001
 * 3. 启动了microservicecloud-consumer-hystrix-dashboard用来监控8001服务提供者
 * 4. 访问http://localhost:8001/hystrix.stream
 *
 * 观察监控窗口
 *
 * 1. 访问http://localhost:9001/hystrix
 * 2. 填写监控地址http://localhost:8001/hystrix.stream,时间2000，title:demo01 ,点击按钮
 *
 * 实心圆：两种含义，它通过颜色的变化代表了实例的健康程度，健康色是从绿色<黄色<橙色<红色递减，
 * 该实心圆除了颜色的变化之外，他的大小也会根据实例的请求流量发生变化，
 * 流量越大该实心圆就越大，所以通过实心圆的展示就可以在大量实例中快速的发现
 * **故障实例和高压力测试**
 *
 */
@SpringBootApplication
@EnableHystrixDashboard
public class DeptConsumer_DashBoard_App {

    public static void main(String[] args){
        SpringApplication.run(DeptConsumer_DashBoard_App.class, args);
    }

}

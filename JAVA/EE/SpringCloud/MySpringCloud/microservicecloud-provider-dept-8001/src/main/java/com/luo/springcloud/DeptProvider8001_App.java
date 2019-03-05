package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 总结
 *
 * - microservicecloud-provider-dept-8001注册到EurekaServer服务中心
 * - microservicecloud-provider-dept-8001将Controller中的某一个方法暴露出去（提供服务发现）
 * - microservicecloud-consumer-dept-80中的Controller就可以调用微服务暴露出来的接口
 */
@SpringBootApplication
@EnableEurekaClient// 本服务启动后会注册到Eureka服务注册中心
@EnableDiscoveryClient// 服务发现
public class DeptProvider8001_App {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8001_App.class, args);
    }

    // 测试服务是否注册成功

    // 先启动Eureka服务注册中心microservicecloud-eureka-7001，
    // 再启动微服务microservicecloud-provider-dept-8001，
    // 打开浏览器输入http://localhost:7001/，
    // Application下出现**MICROSERVICECLOUD-DEPT**微服务名称，
    // 这个名称来源于microservicecloud-provider-dept-8001中application.ym文件中的配置属性，如下

    /*
    spring:
       application:
        name: microservicecloud-dept
    */


    // 测试服务发现：
    // 启动服务注册中心microservicecloud-eureka-7001，再启动microservicecloud-provider-dept-8001，
    // 访问http://localhost:8001/dept/discovery可以得到这个服务的info信息，
    // /dept/discovery接口就是microservicecloud-provider-dept-8001这个服务暴露给外部访问的接口。
    // 使用http://localhost:8001/dept/discovery测试，就是自己测试能不能使用
}

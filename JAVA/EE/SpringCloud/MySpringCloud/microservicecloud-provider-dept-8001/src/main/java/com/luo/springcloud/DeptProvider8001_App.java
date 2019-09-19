package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 测试服务是否注册成功:
 * 1. 先启动Eureka服务注册中心
 * 2. 再启动微服务microservicecloud-provider-dept-8001
 * 3. 打开浏览器输入http://localhost:7001/,页面的Application下出现MY-MICROSERVICECLOUD-DEPT-8001微服务名称
 *
 * 2. 访问http://localhost:8001/dept/discovery可以得到这个服务的info信息，
 * 3. /dept/discovery接口就是microservicecloud-provider-dept-8001这个服务暴露给外部访问的接口。
 * 4. 使用http://localhost:8001/dept/discovery测试，就是自己测试能不能使用
 */
@SpringBootApplication
@EnableEurekaClient
//@EnableDiscoveryClient// 服务发现
public class DeptProvider8001_App {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8001_App.class, args);
    }

}

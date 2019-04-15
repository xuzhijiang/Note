package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 负载均衡自测:
 *
 * 访问连接http://localhost:8001/dept/list，http://localhost:8002/dept/list，
 * http://localhost:8003/dept/list得到不同数据库数
 *
 * 当我们启动服务注册中心7001,7002,7003,再启动80客户端，
 * 这个时候访问localhost/consumer/dept/list，每次刷新就会得到不同数据库的数据。
 * 这就是Ribbon默认的轮询算法的负载均衡。
 *
 */
@SpringBootApplication
@EnableEurekaClient// 本服务启动后会注册到Eureka服务注册中心
@EnableDiscoveryClient// 服务发现
public class DeptProvider8002_App {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8002_App.class, args);
    }

}

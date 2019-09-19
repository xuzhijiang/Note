package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 这个时候访问localhost/consumer/dept/list，每次刷新就会得到不同数据库的数据。
 * 这就是Ribbon默认的轮询算法的负载均衡。
 */
@SpringBootApplication
@EnableEurekaClient
public class DeptProvider8002_App {

    public static void main(String[] args){
        SpringApplication.run(DeptProvider8002_App.class, args);
    }

}

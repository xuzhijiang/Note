package com.funtl.hello.spring.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 测试
 * 浏览器端访问：http://localhost:8888/config-client/dev/master 显示如下：
 *
 * 证明配置服务中心可以从远程程序获取配置信息
 *
 * 附：HTTP 请求地址和资源文件映射
 * http://ip:port/{application}/{profile}[/{label}]
 * http://ip:port/{application}-{profile}.yml
 * http://ip:port/{label}/{application}-{profile}.yml
 * http://ip:port/{application}-{profile}.properties
 * http://ip:port/{label}/{application}-{profile}.properties
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer// 通过 @EnableConfigServer 注解，开启配置服务器功能
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}

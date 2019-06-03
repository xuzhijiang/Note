package com.funtl.hello.spring.cloud.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 入口类没有需要特殊处理的地方，代码如下：
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
/**
 * 附：开启 Spring Boot Profile
 *
 * 我们在做项目开发的时候，生产环境和测试环境的一些配置可能会不一样，有时候一些功能也可能会不一样，
 * 所以我们可能会在上线的时候手工修改这些配置信息。但是 Spring 中为我们提供了 Profile 这个功能。
 * 我们只需要在启动的时候添加一个虚拟机参数，激活自己环境所要用的 Profile 就可以了。
 *
 * 操作起来很简单，只需要为不同的环境编写专门的配置文件，如：application-dev.yml、application-prod.yml， 启动项目时只需要增加一个命令参数 --spring.profiles.active=环境配置 即可，启动命令如下：
 *
 * java -jar hello-spring-cloud-web-admin-feign-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
 * */
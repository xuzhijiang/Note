package com.funtl.hello.spring.cloud.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.internal.EnableZipkinServer;

// 测试追踪
// 启动全部项目，打开浏览器访问：http://localhost:9411/

// 测试之前项目中的接口
// 点击 Find a trace，可以看到具体服务相互调用的数据
// 点击 Dependencies，可以发现服务的依赖关系
@SpringBootApplication
// 通过 @EnableZipkinServer 注解开启 Zipkin Server 功能
@EnableZipkinServer
@EnableEurekaClient
public class ZipKinApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipKinApplication.class, args);
    }

}

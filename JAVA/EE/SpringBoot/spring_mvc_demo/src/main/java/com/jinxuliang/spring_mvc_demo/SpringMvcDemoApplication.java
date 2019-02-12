package com.jinxuliang.spring_mvc_demo;

import com.jinxuliang.spring_mvc_demo.domain.MyDataClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 1. access: localhost:8080
// 2. localhost:8080/mvc/index
// 3. localhost:8080/api/hello

// 派生自SpringBootServletInitializer类
@SpringBootApplication
public class SpringMvcDemoApplication extends SpringBootServletInitializer {

    // 重写configure
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringMvcDemoApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringMvcDemoApplication.class, args);


    }
}

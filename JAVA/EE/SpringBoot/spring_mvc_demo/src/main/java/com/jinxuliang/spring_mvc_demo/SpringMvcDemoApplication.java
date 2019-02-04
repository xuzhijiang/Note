package com.jinxuliang.spring_mvc_demo;

import com.jinxuliang.spring_mvc_demo.domain.MyDataClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringMvcDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringMvcDemoApplication.class);
    }

    public static void main(String[] args) {

        SpringApplication.run(SpringMvcDemoApplication.class, args);


    }
}

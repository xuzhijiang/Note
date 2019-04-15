package com.jinxuliang.spring_mvc_controller_demo;

import com.jinxuliang.spring_mvc_controller_demo.annotation.ContentService;
import com.jinxuliang.spring_mvc_controller_demo.annotation.EnableContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableContentService(policy = "core")
public class SpringMvcControllerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcControllerDemoApplication.class, args);
    }


}

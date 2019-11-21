package com.springboot.beanscope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BeanscopeApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BeanscopeApplication.class, args);
    }
}

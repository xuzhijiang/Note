package com.spring.bean.scope.core.WebApplicationContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// WebApplicationContext环境测试
@SpringBootApplication
public class SpringBootBeanScopeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootBeanScopeApplication.class, args);
    }
}

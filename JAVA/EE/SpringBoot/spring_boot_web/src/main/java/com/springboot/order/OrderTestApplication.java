package com.springboot.order;

/**
 * 测试Filter,Interceptor,以及AOP的执行顺序
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderTestApplication.class, args);
    }

}

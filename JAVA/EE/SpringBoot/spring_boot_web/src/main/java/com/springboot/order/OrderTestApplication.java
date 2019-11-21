package com.springboot.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试Filter,Interceptor,以及AOP的执行顺序
 */
@SpringBootApplication
public class OrderTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderTestApplication.class, args);
    }

}

package org.mq.core.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(basePackages = "org.mq.core.dao")
@EnableScheduling
public class AlipayServerApp {
    public static void main(String[] args) {
        SpringApplication.run(AlipayServerApp.class, args);
    }
}

package org.mq.core.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.mq.core.dao")
public class TicketServer12306 {
    public static void main(String[] args) {
        SpringApplication.run(TicketServer12306.class, args);
    }
}

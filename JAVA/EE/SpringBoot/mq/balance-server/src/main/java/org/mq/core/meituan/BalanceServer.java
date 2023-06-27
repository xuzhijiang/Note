package org.mq.core.meituan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.mq.core.meituan.mapper")
public class BalanceServer {
    public static void main(String[] args) {
        SpringApplication.run(BalanceServer.class, args);
    }
}

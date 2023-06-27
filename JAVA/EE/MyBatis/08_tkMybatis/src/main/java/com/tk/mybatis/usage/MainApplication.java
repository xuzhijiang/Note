package com.tk.mybatis.usage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 需要使用 @MapperScan 注解来指定 Mapper 接口的路径
// 注意这里的 @MapperScan 注解是 tk.mybatis.spring.annotation.MapperScan; 包下的
@MapperScan(basePackages = "com.tk.mybatis.usage.mapper")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

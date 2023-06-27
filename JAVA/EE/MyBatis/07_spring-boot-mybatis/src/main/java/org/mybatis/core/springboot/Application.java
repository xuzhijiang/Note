package org.mybatis.core.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @MapperScan和@Mapper注解是肯定要选择一个的,必须要告诉mybatis mapper接口有哪些
// @Mapper或者@MapperScan将接口扫描装配到容器中
// @MapperScan作用: org.mybatis.core.springboot.mapper包下的所有接口都会自动的相当于添加@Mapper注解
// 如果没有启用@MapperScan，则需要给mapper接口加上@Mapper注解
@MapperScan("org.mybatis.core.springboot.mapper")
public class Application {
	public static void main(String[] args) { SpringApplication.run(Application.class, args);}
}

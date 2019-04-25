package com.jinxuliang.mybatisspring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 创建Spring boot的项目中引入MyBatis依赖(勾选MySQL和MyBatis)
@SpringBootApplication
// 启用Mapper扫描，如果不加这个注解，则需要为每个MyBatis Mapper接口添加@Mapper注解
@MapperScan("com.jinxuliang.mybatisspring.mapper")
public class MybatisspringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisspringApplication.class, args);
	}

}

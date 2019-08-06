package com.springframework.datajpa.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 对数据库的操作无非就“增删改查”。语句都是类似的，
 * 开发人员需要写大量类似而枯燥的语句来完成业务逻辑。
 *
 * 为了解决这些大量枯燥的数据操作语句，orm框架以操作Java对象的方式来操作数据库
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

}

package com.springframework.datajpa.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// CrudRepository: 通过使用CrudRepository减少通用CRUD操作的代码大小
@SpringBootApplication
@EnableJpaRepositories
@EnableSwagger2
public class DataJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}
}

package com.itranswarp.springcloud.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// 测试Swagger: http://localhost:8080/swagger-ui.html
@SpringBootApplication
public class RestApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RestApplication.class, args);
	}

}

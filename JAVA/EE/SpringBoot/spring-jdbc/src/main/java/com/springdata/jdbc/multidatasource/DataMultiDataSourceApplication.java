package com.springdata.jdbc.multidatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DataMultiDataSourceApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DataMultiDataSourceApplication.class, args);
	}
}

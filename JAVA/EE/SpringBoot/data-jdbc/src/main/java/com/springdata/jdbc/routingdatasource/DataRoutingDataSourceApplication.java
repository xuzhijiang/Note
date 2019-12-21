package com.springdata.jdbc.routingdatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
// 不要SpringBoot帮我们配置DataSource,而是我们自己配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataRoutingDataSourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataRoutingDataSourceApplication.class, args);
	}
}

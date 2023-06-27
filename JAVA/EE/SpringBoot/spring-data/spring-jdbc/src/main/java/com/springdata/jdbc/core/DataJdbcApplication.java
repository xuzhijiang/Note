package com.springdata.jdbc.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@EnableSwagger2
public class DataJdbcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DataJdbcApplication.class, args);

		testConnection(ctx);
	}

	//测试数据库的连接
	private static void testConnection(ConfigurableApplicationContext context) {
		DataSource dataSource= (DataSource) context.getBean(DataSource.class);
		System.out.println("\n\t DataSource的具体实现类型：" + dataSource.getClass());
		System.out.println("\n\t DataSource：" + dataSource);
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("己连接,datasource");
		} catch (SQLException e) {
			System.out.println("未能连接:"+ e.getMessage());
		}
	}

}

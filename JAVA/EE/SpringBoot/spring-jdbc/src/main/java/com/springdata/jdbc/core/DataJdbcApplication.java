package com.springdata.jdbc.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 1. JDBC是历史悠久的Java访问关系型数据库的技术，直到今天，它仍然是许多数据存取框架底层的技术，但代码比较冗余，为人所诟病。
 * 2. Spring提供了一个JdbcTemplate对JDBC进行了封装，配合Spring Boot的自动配置功能，能比较好地消除了直接使用原生JDBC所带来的冗余代码。
 *
 * 1. 对于比较简单的项目，使用JDBC去访问数据库是可行的，但对于真实的项目，现在很少直接使用它了，所以，对这块内容，了解即可。
 * 3. Spring JDBC技术比较直观易学
 */
@SpringBootApplication
@EnableSwagger2
public class DataJdbcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DataJdbcApplication.class, args);

		testConnection(ctx);
	}

	//测试数据库的连接
	private static void testConnection(ConfigurableApplicationContext context) {
		DataSource dataSource= (DataSource) context.getBean("dataSource");
		System.out.println("\n数据源对象类型：" + dataSource);
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("己连接");
		} catch (SQLException e) {
			System.out.println("未能连接:"+ e.getMessage());
		}
	}

}

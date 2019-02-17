package com.journaldev.spring.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

// Spring DataSource配置
// 下一步是创建spring配置类来定义DataSource bean。 
// 我使用基于java的配置，你也可以使用spring bean配置xml文件。

//@Configuration  - 表示此类是Spring上下文的配置。
//@ComponentScan(“com.journaldev.spring”） - 指定要扫描组件类的包。
//@PropertySource(“classpath：database.properties”） - 表示将从database.properties文件中读取属性。
@Configuration
@ComponentScan("com.journaldev.spring")
@PropertySource("classpath:database.properties")
public class AppConfig {

	@Autowired
	Environment environment;

	private final String URL = "url";
	private final String USER = "dbuser";
	private final String DRIVER = "driver";
	private final String PASSWORD = "dbpassword";

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(environment.getProperty(URL));
		driverManagerDataSource.setUsername(environment.getProperty(USER));
		driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
		driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
		return driverManagerDataSource;
	}
}

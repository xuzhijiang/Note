package com.journaldev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import com.journaldev.drivers.DataBaseDriver;
import com.journaldev.drivers.MySqlDriver;
import com.journaldev.drivers.OracleDriver;

@Configuration// @Configuration：用于表示此类声明了一个或多个@Bean方法。这些类(用@Configuration修饰的类)由Spring容器处理，以在运行时为这些bean生成bean定义和服务请求
@ComponentScan("com.journaldev")//@ComponentScan：配置与使用@Configuration注解修饰的类一起使用的组件扫描指令。//在这里，我们可以指定扫描spring组件的基础包。
@PropertySource("classpath:oracledatabase.properties")// @PropertySource：提供了一个简单的声明机制，用于向Spring的Environment添加property source。// 有一个类似的注释(即@PropertySources)用于添加一个属性源文件数组(array of property source files)
public class AppConfig {

	// @Autowired：Spring @Autowired注释用于自动注入bean。
	@Autowired
    Environment environment;
	
	// 注意oracleDriver的bean定义。 在这个方法中，我们从oracledatabase.properties文件中读取属性，
	// 该文件由Spring框架设置为环境变量。
	@Bean
	DataBaseDriver rrr() {
		OracleDriver oracleDriver = new OracleDriver();
		oracleDriver.setDriver(environment.getProperty("db.driver"));
        oracleDriver.setUrl(environment.getProperty("db.url"));
        oracleDriver.setPort(Integer.parseInt(environment.getProperty("db.port")));
        oracleDriver.setUser(environment.getProperty("db.user"));
        oracleDriver.setPassword(environment.getProperty("db.password"));
        return oracleDriver;
	}

	// @Bean：表示一个方法生成一个由Spring容器管理的bean。 这是最常用和最重要的Spring注释之一。
	@Bean
	DataBaseDriver mmm() {
		return new MySqlDriver();
	}
}

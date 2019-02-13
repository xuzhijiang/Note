package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通过上面这个简单的例子，我们可以看到在Spring Boot下访问数据库的配置依然秉承了框架的初衷：简单。
 * 我们只需要在pom.xml中加入数据库依赖，再到application.properties中配置连接信息，
 * 不需要像Spring应用中创建JdbcTemplate的Bean，就可以直接在自己的对象中注入使用。
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}

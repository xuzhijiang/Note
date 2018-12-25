package com.journaldev.spring.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// 基于annotation的配置类，用于初始化Spring容器。

//3. (Java Based Configuration)基于Java的配置 - 从Spring 3.0开始，我们可以使用java程序配置Spring bean。
//用于基于java的配置的一些重要注释是@Configuration，@ComponentScan和@Bean。

@Configuration
@ComponentScan(value="com.journaldev.spring.main")
public class MyConfiguration {

	@Bean
	public MyService getService(){
		return new MyService();
	}
	
}

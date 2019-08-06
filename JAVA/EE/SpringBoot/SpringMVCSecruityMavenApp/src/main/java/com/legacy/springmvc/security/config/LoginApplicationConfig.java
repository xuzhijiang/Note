package com.legacy.springmvc.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

// 1. 定义Spring MVC View Resolvers以避免编写“web.xml”文件。

// @EnableWebMvc Annotation用于在Spring Framework中启用Spring Web MVC应用程序功能
// @Import Annotation用于将Spring Security Configuration类导入此类。
@EnableWebMvc
@Configuration
@ComponentScan({ "com.journaldev.spring.*" })
@Import(value = { LoginSecurityConfig.class })
public class LoginApplicationConfig {
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
}
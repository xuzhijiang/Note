package com.journaldev.spring.di.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.journaldev.spring.di.services.EmailService;
import com.journaldev.spring.di.services.MessageService;
// 带annotation的Spring依赖注入配置

// 对于基于注解的配置，我们需要写一个Configuration类，
// 该类将用于将当前实现bean注入组件属性(inject component property)。

// 1. @Configuration annotation is used to let Spring know that it’s a Configuration class.

// 2. @ComponentScan annotation is used with @Configuration annotation 
// to specify the packages to look for Component classes.
// @ComponentScan注解和@Configuration注解一起使用去指定搜索组件类的包

// 3. @Bean annotation is used to let Spring framework know that this method 
// should be used to get the bean implementation to inject in Component classes.
// @Bean注解用于让Spring框架知道应该使用此方法来获取 要在Component类中注入的bean的实现。

@Configuration
@ComponentScan(value={"com.journaldev.spring.di.consumer"})
public class DIConfiguration {

	@Bean
	public MessageService getMessageService(){
		return new EmailService();
	}
}

package com.journaldev.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 因此，最好将@Configuration注释与配置类一起使用，
// 以确保我们的spring容器的行为与我们希望的方式相同。

//如果您不想出于某些奇怪的原因使用@Configuration批注，
// 我们仍然可以通过不调用myBean（）方法创建配置类 而是使用通过@Autowired注解
// 配置的MyBean实例变量来。 像下面代码的东西也可以。(即可以把@Configuration注释掉)

public class MyConfiguration2 {
	
	@Autowired
	MyBean myBean;
	
	@Bean
    public MyBean myBean() {
		return new MyBean();
	}
	
	@Bean
    public MyBeanConsumer myBeanConsumer() {
		return new MyBeanConsumer(myBean);
	}
}

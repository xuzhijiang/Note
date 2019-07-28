package com.springboot.beanscope.config;

import com.springboot.beanscope.bean.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ComponentScan("com.springboot.beanscope.bean")
public class BeanConfig {

    // 指定POJO类作为Bean的初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destroy")
    InitAndDestoryBean initAndDestoryBean() {
        return new InitAndDestoryBean();
    }

    @Bean
    @Scope("singleton")
    public TestSingletonBean getSingletonBean() {
        return new TestSingletonBean("singleton");
    }

    // 每次从spring容器请求时创建
    @Bean
    @Scope("prototype")
    public TestPrototypeBean getPrototypeBean() {
        return new TestPrototypeBean("prototype");
    }

    // 将得到异常
    // 因为request, session and global-session scopes不适用于独立应用程序。
    // singleton和prototype scope的能用于独立应用程序.
	@Bean
	@Scope(WebApplicationContext.SCOPE_REQUEST)
	public TestRequestBean getRequestBean() {
		return new TestRequestBean("request");
	}

}

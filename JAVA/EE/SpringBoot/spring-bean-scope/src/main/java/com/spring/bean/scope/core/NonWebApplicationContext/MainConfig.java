package com.spring.bean.scope.core.NonWebApplicationContext;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
// 必须加上这个@ComponentScan才可以扫描到InstF
@ComponentScan(basePackages = "com.spring.bean.scope.core.NonWebApplicationContext")
public class MainConfig {

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    InstE getEmployee()  { // 注意方法不能为private
        return new InstE();
    }

}

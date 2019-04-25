package com.jinxuliang.beanscope.config;

import com.jinxuliang.beanscope.beans.InitAndDestoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    //指定POJO类作为Bean的初始化和销毁方法
    @Bean(initMethod = "init", destroyMethod = "destory")
    InitAndDestoryBean initAndDestoryBean() {
        return new InitAndDestoryBean();
    }
}

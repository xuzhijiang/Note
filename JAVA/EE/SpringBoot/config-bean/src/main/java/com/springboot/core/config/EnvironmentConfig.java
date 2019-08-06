package com.springboot.core.config;

import com.springboot.core.bean.EnvironmentBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:environment.properties")
public class EnvironmentConfig {

    @Autowired
    Environment environment;

    @Bean
    EnvironmentBean getEnvironmentBean() {
        EnvironmentBean bean = new EnvironmentBean();
        bean.setName(environment.getProperty("environmentBean.name"));
        return bean;
    }

}
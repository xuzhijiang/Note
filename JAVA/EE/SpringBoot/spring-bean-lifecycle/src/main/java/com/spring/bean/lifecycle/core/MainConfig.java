package com.spring.bean.lifecycle.core;

import com.spring.bean.lifecycle.core.beans.InstB;
import com.spring.bean.lifecycle.core.beans.InstE;
import com.spring.bean.lifecycle.core.beans.InstF;
import com.spring.bean.lifecycle.core.beans.InstC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    @Bean
    InstB instB() {
        return new InstB();
    }

    // @Bean注释也可以与name，等参数一起使用。
    // name  - 允许你给bean命名, initMethod和destroyMethod指定这个Bean的Post初始化和Pre销毁方法
    @Bean(name = "instC-name", initMethod = "init", destroyMethod = "destroy")
    InstC initAndDestoryBean() {
        return new InstC();
    }

    @Bean
    InstE instE() {
        return new InstE();
    }

    @Bean
    InstF instF() {
        return new InstF();
    }

}

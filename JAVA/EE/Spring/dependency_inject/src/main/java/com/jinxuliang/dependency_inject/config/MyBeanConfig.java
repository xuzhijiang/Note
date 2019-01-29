package com.jinxuliang.dependency_inject.config;

import com.jinxuliang.dependency_inject.bean.POJOBean;
import com.jinxuliang.dependency_inject.bean.POJOBeanContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
//使用@ComponentScan指定额外的包扫描组件
@ComponentScan({"com.jinxuliang.dependency_inject.other"})
public class MyBeanConfig {

    //使用@Bean这个注解，
    //定义那些没有@Component注解的普通Java类
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    //将POJOBean通过构造方法注入到POJOBeanContainer中
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){

        return  new POJOBeanContainer(pojoBean());
    }
}


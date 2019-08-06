package com.di.config;

import com.di.bean.POJOBean;
import com.di.bean.POJOBeanContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 使用@ComponentScan指定额外的要扫描组件的包(也就是除了主应用的包以及子包 之外的包.)
// 加入@ComponentScan注解之后，放在com.javax.other.bean包下的bean就能被识别了
@ComponentScan({"com.javax.other.other"})
public class MyBeanConfig {

    // 使用@Bean这个注解，定义那些没有@Component注解的普通Java类
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    // 外界通过IoC容器可以按名称(即"beanContainer")获取Bean的实例
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){
        return  new POJOBeanContainer(pojoBean());
    }
}
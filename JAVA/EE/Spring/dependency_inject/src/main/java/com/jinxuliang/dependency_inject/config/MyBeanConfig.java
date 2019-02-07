package com.jinxuliang.dependency_inject.config;

import com.jinxuliang.dependency_inject.bean.POJOBean;
import com.jinxuliang.dependency_inject.bean.POJOBeanContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 使用@ComponentScan指定额外的要扫描组件的包
// 加入@ComponentScan注解之后，放在other包下的组件就能被识别了。
@ComponentScan({"com.javax.other.bean"})
public class MyBeanConfig {

    // 使用@Bean这个注解，定义那些没有@Component注解的普通Java类
    // @Bean：在@Configuration注解修饰的类中附加于特定的方法之上，方法
    // 返回的对象将成为一个Spring可以管理的Bean。
    @Bean
    POJOBean pojoBean(){
        return new POJOBean();
    }

    // 将POJOBean通过构造方法注入到POJOBeanContainer中
    // 外界通过IoC容器可以按名称(即"beanContainer")获取Bean的实例
    @Bean(name = "beanContainer")
    POJOBeanContainer pojoBeanContainer(){
        return  new POJOBeanContainer(pojoBean());
    }
}
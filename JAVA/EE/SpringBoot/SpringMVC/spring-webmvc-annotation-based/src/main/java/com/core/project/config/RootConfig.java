package com.core.project.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @vlog:
 * @desc: 类的描述: IOC根容器(父容器),不扫描Controller的注解和WebAppConfig.class
 *                  父容器不扫描controller,只扫描service和dao
 * @author
 */
@Configuration // spring3.0的注解
// @ComponentScan扫描出来的是BeanDefinition,IoC会根据Bean定义生成bean实例
@ComponentScan(basePackages = "com.core.project", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = {RestController.class, Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebAppConfig.class)
})
public class RootConfig {

    /**
     * RootConfig里面配置的bean以前需要在这样配置:
     *
     * <context-param>
     *     <param-name>contextConfigLocation</param-name>
     *     <param-value>classpath:spring-context*.xml</param-value>
     * </context-param>
     * <listener>
     *    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     * </listener>
     */

}

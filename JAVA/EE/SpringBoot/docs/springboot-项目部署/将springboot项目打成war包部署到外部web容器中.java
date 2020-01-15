package com.springboot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// 在org.springframework:spring-web这个jar包下的META-INF/services中有一个名为:
// javax.servlet.ServletContainerInitializer的文件,里面内容是这个javax.servlet.ServletContainerInitializer
// 接口的实现类: org.springframework.web.SpringServletContainerInitializer

// 真实的tomcat启动的时候会调用:
// WebappServiceLoader<ServletContainerInitializer> loader = new WebappServiceLoader<>(context);
// loader.load(ServletContainerInitializer.class);
// 然后SpringServletContainerInitializer的onStartup()会被调用.

// SpringServletContainerInitializer的onStartup()中会去调用WebApplicationInitializer
// 的实现类的onStartup(),

// 本例SpringBootServletInitializer就是就是WebApplicationInitializer的实现类,
// 所以SpringBootWebApplication的onStartup()会被调用.进而SpringBootWebApplication的
// configure被调用

// 总结: SpringBootServletInitializer是WebApplicationInitializer的子类,
// 使得这个项目可以打成可war包,从而部署到外部的web容器上的
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

    private static final Class<SpringBootWebApplication> applicationClass = SpringBootWebApplication.class;

    public static void main(String[] args) {
         SpringApplication.run(SpringBootWebApplication.class, args);
    }

    // SpringBootWebApplication继承SpringBootServletInitializer，并重写里面的configure方法:
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注册applicationClass作为配置类.
        return builder.sources(applicationClass);
    }

}

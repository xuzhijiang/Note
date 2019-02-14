package com.jinxuliang.spring_advanced_demo.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 拦截器的配置文件
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("添加自定义的拦截器");
        //在这里将前面定义的两个拦截器加入
        registry.addInterceptor(new MyWebInterceptor());
        registry.addInterceptor(new WebStopWatch());
    }
}

//测试拦截器

// 打开浏览器访问:localhost:8080/hello,即访问HelloController
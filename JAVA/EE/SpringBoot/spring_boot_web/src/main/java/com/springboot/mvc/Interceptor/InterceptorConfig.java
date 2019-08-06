package com.springboot.mvc.Interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 拦截器的配置类
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("添加自定义的拦截器");
        // 添加两个拦截器
        // 如果配置了多个spring interceptors，则按配置顺序执行preHandle(）方法，
        // 而以相反顺序(in the reverse order)调用postHandle(）和afterCompletion(）方法。
        registry.addInterceptor(new MyWebInterceptor());
        registry.addInterceptor(new WebStopWatch());
    }
}

package com.jinxulaing.webjar_demo.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

// 扩展SpringMVC

//使用WebMvcConfigurationSupport扩展SpringMVC的功能
// 不能标注@EnableWebMvc注解，需要扩展什么功能，就重写什么方法
@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送lll请求就直接来到success页面
        registry.addViewController("lll").setViewName("success");
    }
}

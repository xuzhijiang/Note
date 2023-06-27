package com.spring.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //继承WebMvcConfigurerAdapter，重写addInterceptor方法，利用registry添加拦截器
        registry.addInterceptor(myInterceptor);
    }

    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new MyFilter());

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/home");

        filterFilterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterFilterRegistrationBean;
    }

}

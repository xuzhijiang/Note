package com.springboot.mvc.config;

import com.springboot.mvc.Interceptor.MyWebInterceptor;
import com.springboot.mvc.Interceptor.WebStopWatch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

// 在springboot中不要使用@EnableWebMvc,因为会使一些springboot的功能失效
// 添加上@EnableWebMvc之后,springmvc就要全面接管,但是此时springmvc其实只配置了一些基础功能.就会导致springboot的一些功能失效
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("添加自定义的拦截器");
        // 添加两个拦截器
        // 如果配置了多个spring interceptors，则按配置顺序执行preHandle(）方法，
        // 而以相反顺序(in the reverse order)调用postHandle(）和afterCompletion(）方法。
        registry.addInterceptor(new MyWebInterceptor());
        // registry.addInterceptor(new MyWebInterceptor()).addPathPatterns("/*");
        registry.addInterceptor(new WebStopWatch());
    }

    /*
    // 配置视图解析器
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(1024*1024*10);// 10M
        return commonsMultipartResolver;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    // 请求视图解析
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
    }
    */
}

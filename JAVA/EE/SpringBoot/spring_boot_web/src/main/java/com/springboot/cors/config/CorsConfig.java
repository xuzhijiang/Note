package com.springboot.cors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * java配置的方式配置跨域
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ajaxConfig") // 允许跨域访问的url
        // registry.addMapping("/**") // 允许跨域访问的url
                .allowedOrigins("*") // 放行哪些原始域
                .allowedMethods("GET", "POST") //放行哪些原始域(请求method)
                 .allowedHeaders("*") //放行哪些原始域(头部信息)
                .allowCredentials(false) // 是否发送Cookie信息
                // .exposedHeaders() //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                .maxAge(3600);
    }

}

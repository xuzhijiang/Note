package com.myspringframework.boot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"com.myspringframework.boot"})
// @EnableWebMvc的作用?能不能在这里把这个去掉?
// 不能,因为我们想要把http响应体转换成json格式返回给client,所以要添加一个消息转换器.
// @EnableWebMvc就是用来帮我们添加 消息转换器 的,如果去掉@EnableWebMvc,消息转换器就不会被添加上
@EnableWebMvc
public class MySpringBootConfig implements WebMvcConfigurer {
    // 我们在com.myspringframework.boot.controller.MyBootController中,想返回json,所以要配一个msg转换器
    // 如果你使用的是springboot的话,会自动帮你添加消息转换器.
    // 而如果你使用的是springmvc的话,就没有人帮你添加消息转换器,所以要自己手动添加

    // 我们只需要实现WebMvcConfigurer,然后像下面一样,最后配合@EnableWebMvc 就可以添加消息转换器.
    // 所以这里@EnableWebMvc不可以去掉
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}

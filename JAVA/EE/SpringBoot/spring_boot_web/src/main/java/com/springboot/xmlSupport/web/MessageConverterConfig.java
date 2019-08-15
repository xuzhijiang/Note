package com.springboot.xmlSupport.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 第一步：引入Xml消息转换器
 *
 * 在Spring Boot只需要加入jackson-dataformat-xml依赖，Spring Boot就会自动引入MappingJackson2XmlHttpMessageConverter的实现
 */
@Configuration
public class MessageConverterConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
        builder.indentOutput(true);
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
    }

}

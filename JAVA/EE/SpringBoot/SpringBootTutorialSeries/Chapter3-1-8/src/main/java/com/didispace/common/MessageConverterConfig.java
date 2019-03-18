package com.didispace.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 第一步：引入Xml消息转换器
 *
 * 在传统Spring应用中，我们可以通过如下配置加入对Xml格式数据的消息转换实现：
 *
 * 在Spring Boot应用不用像上面这么麻烦，只需要加入jackson-dataformat-xml依赖，
 * Spring Boot就会自动引入MappingJackson2XmlHttpMessageConverter的实现(这个类在这种应用中没有
 * 所用，只是为了说明传统的Spring应用中如何使用)
 *
 * 在Spring Boot应用不用像上面这么麻烦，只需要加入jackson-dataformat-xml依赖，
 * Spring Boot就会自动引入MappingJackson2XmlHttpMessageConverter的实现：
 *
 * 同时，为了配置Xml数据与维护对象属性的关系所要使用的注解也在上述依赖中，所以这个依赖也是必须的
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

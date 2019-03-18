package com.didispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 实现原理：消息转换器（Message Converter）
 *
 * 在Spring MVC中定义了HttpMessageConverter接口，抽象了消息转换器对类型的判断、
 * 对读写的判断与操作(自己可以看)
 *
 * HTTP请求的Content-Type有各种不同格式定义，如果要支持Xml格式的消息转换，
 * 就必须要使用对应的转换器。Spring MVC中默认已经有一套采用Jackson实现的转换器
 * MappingJackson2XmlHttpMessageConverter
 *
 *
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

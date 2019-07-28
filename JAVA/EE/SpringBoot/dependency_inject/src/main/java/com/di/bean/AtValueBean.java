package com.di.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AtValueBean {

    // 可以使用@Value指定默认值
    @Value("Hello")
    private String info;

    //从application.properties中提取值
    @Value("${message}")
    private String message;

    @Override
    public String toString() {
        return "AtValueBean{" +
                "info='" + info + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

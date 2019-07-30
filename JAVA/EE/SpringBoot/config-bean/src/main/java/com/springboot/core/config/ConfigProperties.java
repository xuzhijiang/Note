package com.springboot.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 从application.properties中加载数据，初始化Bean的相应属性
@Component
@ConfigurationProperties(prefix = "xzj")
public class ConfigProperties {

    private String info;

    private int counter;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "MyProperties{" +
                "info='" + info + '\'' +
                ", counter=" + counter +
                '}';
    }

}
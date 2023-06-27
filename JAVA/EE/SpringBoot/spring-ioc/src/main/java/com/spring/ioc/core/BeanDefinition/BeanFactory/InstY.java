package com.spring.ioc.core.BeanDefinition.BeanFactory;

import org.springframework.beans.factory.annotation.Autowired;

public class InstY {

    private String message;

    public InstY() {
        System.out.println("InstY初始化===========>");
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Y==> [" + message + "]";
    }
}

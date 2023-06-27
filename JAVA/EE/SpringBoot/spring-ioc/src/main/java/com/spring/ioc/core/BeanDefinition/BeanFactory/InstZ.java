package com.spring.ioc.core.BeanDefinition.BeanFactory;

import org.springframework.beans.factory.annotation.Autowired;

public class InstZ {

    @Autowired
    private InstY instY;

    private String message;

    public InstZ() {
        System.out.println("InstZ初始化===========>");
    }


    public void setInstY(InstY instY) {
        this.instY = instY;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[" + message + "]====> instY : " + instY;
    }
}

package com.di.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextAwareBean implements ApplicationContextAware {

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    // 此方法将会在实例化Bean时由Spring Framework自动调用，
    // 从而将一个ApplicationContext对象注入到Bean中
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
        // 获取了Application对象之后，就可以使用它来干IoC容器能干的所有事情……
    }
}

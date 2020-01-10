package com.spring.bean.lifecycle.core.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class InstF implements BeanPostProcessor {

    // 在bean的初始化之前被调用,这个要区别与PostConstruct后置处理
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessorBean before: " + beanName);
        return bean;
    }

    // 在bean初始化完成调用,这个要区别与PreDestroy前置处理
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessorBean after: " + beanName);
        return bean;
    }
}

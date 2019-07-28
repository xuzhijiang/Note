package com.springboot.beanscope.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 实现 BeanPostProcessor 接口，Spring 中所有 bean 在做初始化时都会调用该接口中的两个方法，
 * 可以用于对一些特殊的 bean 进行处理：
 */
public class BeanPostProcessorBean implements BeanPostProcessor {

    // 预初始化 初始化之前调用
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessorBean before: " + beanName);
        return bean;
    }

    // 后初始化  bean 初始化完成调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessorBean after: " + beanName);
        return bean;
    }
}

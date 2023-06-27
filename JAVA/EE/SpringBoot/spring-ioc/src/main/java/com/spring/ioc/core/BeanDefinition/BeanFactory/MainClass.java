package com.spring.ioc.core.BeanDefinition.BeanFactory;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * BeanFactory这种ioc容器测试
 */
public class MainClass {
    public static void main(String[] args) {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring.xml"));
        System.out.println("ioc容器创建完成=============>");
        // BeanFactory这种ioc容器是懒加载,第一次获取的时候才会进行初始化
        InstZ instZ = beanFactory.getBean(InstZ.class);
        System.out.println(instZ);
    }
}

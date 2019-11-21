package com.spring.beans.BeanDefinition.setInject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// IoC有2个环节,第一个是把所有的BeanDefinition解析出来存放到BeanDefinitionMap<beanName, BeanDefinition>中
// 然后就是从BeanDefinitionMap中挨个的取出来进行实例化
public class MainClass {

    public static void main(String[] args) {
        //AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class, InstA.class, InstB.class, InstD.class,MyBeanFactoryPostProcessor.class, Person.class);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        //InstD instD = (InstD) ctx.getBean("instA");
        //InstD instD = ctx.getBean("instD");
        //InstA instA = ctx.getBean(InstA.class);
    }

}

package com.jinxuliang.beanscope;

import com.jinxuliang.beanscope.beans.InitAndDestoryAnnotationBean;
import com.jinxuliang.beanscope.beans.InitAndDestoryBean;
import com.jinxuliang.beanscope.beans.Prototype;
import com.jinxuliang.beanscope.beans.Singleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class BeanscopeApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BeanscopeApplication.class, args);

        //演示BeanScope的作用
        testSingletonBean(context);
        testPrototypeBean(context);


        System.out.println("指定Bean的初始化和销毁方法");
        InitAndDestoryBean initAndDestoryBean = context.getBean(InitAndDestoryBean.class);
        System.out.println(initAndDestoryBean);

        InitAndDestoryAnnotationBean initAndDestoryAnnotationBean = context.getBean(InitAndDestoryAnnotationBean.class);
        System.out.println(initAndDestoryAnnotationBean);
    }

    private static void testPrototypeBean(ApplicationContext context) {
        //Prototype Scope模式，每次请求都实例化一个对象。
        System.out.println("Prototype模式的两个Bean");
        Prototype prototype1 = context.getBean(Prototype.class);
        Prototype prototype2 = context.getBean(Prototype.class);
        System.out.println(prototype1 == prototype2); //输出：false
    }

    private static void testSingletonBean(ApplicationContext context) {
        //Singleton模式，始终只有一个实例
        System.out.println("Singleton模式的两个Bean");
        Singleton singleton1 = context.getBean(Singleton.class);
        Singleton singleton2 = context.getBean(Singleton.class);
        System.out.println(singleton1 == singleton2); //输出：true
    }
}

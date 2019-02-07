package com.jinxuliang.beanscope.beans;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//使用注解定义初始化与销毁时自动调用的方法
@Component
public class InitAndDestoryAnnotationBean {

    //对象构造完毕时调用
    @PostConstruct
    public void init(){
        System.out.println("InitAndDestoryAnnotationBean's PostConstruct method.");
    }

    //对象销毁前调用
    @PreDestroy
    public void destory(){
        System.out.println("InitAndDestoryAnnotationBean's PreDestroy method.");
    }
}



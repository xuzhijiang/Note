package com.spring.bean.lifecycle.core.beans;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class InstA {

    // 对象构造完毕时调用
    // 仅当所有bean的post-init方法执行正确初始化后，才返回容器上下文context。
    @PostConstruct
    public void init() { System.out.println(getClass() + "\t init"); }

    // 对象销毁前调用
    // 调用了ioc的close才会调用bean的PreDestroy对应的方法.
    // 当容器上下文被关闭时，bean按照它们被初始化的相反顺序被销毁，即以LIFO(后进先出）顺序。
    @PreDestroy
    public void destory(){
        System.out.println(getClass() + "\t destroy");
    }
}


package com.jinxuliang.dependency_inject.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

// 当一个Bean需要访问容器中的其它Bean，或者需要访问外部资源时，
// 可以让其实现ApplicationContextAware，从而获取对外部ApplicationContext的引用
@Component
public class ContextAwareBean implements ApplicationContextAware {

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    //此方法将会在实例化Bean时由Spring Framework自动调用，
    // 从而将一个ApplicationContext对象注入到Bean中
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
        // 获取了Application对象之后，就可以使用它来干IoC容器能干的所有事情……
    }
}

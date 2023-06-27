package com.springboot.advanced.applicationevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

// 事件发布者
@Component
public class MyEventPublisher {

    //要触发事件，需要注入ApplicationContext
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String message){
        // 使用ApplicationContext发布事件
        applicationContext.publishEvent(new MyEvent(this,message));
    }
}
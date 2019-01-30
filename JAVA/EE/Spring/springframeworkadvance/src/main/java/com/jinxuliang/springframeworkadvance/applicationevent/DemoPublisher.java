package com.jinxuliang.springframeworkadvance.applicationevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

//可触发事件的Bean
@Component
public class DemoPublisher {
    //要触发事件，需要注入ApplicationContext
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String message){
        //使用ApplicationContext发布事件
        applicationContext.publishEvent(
                new DemoEvent(this,message));
    }
}


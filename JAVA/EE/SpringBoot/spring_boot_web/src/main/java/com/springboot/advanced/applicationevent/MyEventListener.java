package com.springboot.advanced.applicationevent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//定义一个事件监听者
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent demoEvent) {
        System.out.println("收到事件对象："+demoEvent);
    }
}


package com.jinxuliang.springframeworkadvance.applicationevent;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//定义一个事件监听者
@Component
public class DemoEventListener
        implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        System.out.println("收到事件对象："+demoEvent);
    }
}


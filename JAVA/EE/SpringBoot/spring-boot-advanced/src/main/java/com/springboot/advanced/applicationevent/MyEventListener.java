package com.springboot.advanced.applicationevent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 如果一个bean实现了ApplicationListener接口，当一个ApplicationEvent 被发布以后，bean会自动被通知。

// 事件监听者
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("事件监听者 => 收到事件=====>："+myEvent);
    }
}
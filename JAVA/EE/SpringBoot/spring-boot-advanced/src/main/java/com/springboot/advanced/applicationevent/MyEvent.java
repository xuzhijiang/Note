package com.springboot.advanced.applicationevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

// 定义事件对象

/**
 * Spring 提供了以下5种标准的事件：
 *
 * 1）上下文更新事件（ContextRefreshedEvent）：在调用ConfigurableApplicationContext 接口中的refresh()方法时被触发。
 * 2）上下文开始事件（ContextStartedEvent）：当容器调用ConfigurableApplicationContext的Start()方法开始/重新开始容器时触发该事件。
 * 3）上下文停止事件（ContextStoppedEvent）：当容器调用ConfigurableApplicationContext的Stop()方法停止容器时触发该事件。
 * 4）上下文关闭事件（ContextClosedEvent）：当ApplicationContext被关闭时触发该事件。容器被关闭时，其管理的所有单例Bean都被销毁。
 * 5）请求处理事件（RequestHandledEvent）：在Web应用中，当一个http请求（request）结束触发该事件。
 *
 * 这些事件都是ApplicationContextEvent的子类.如果一个bean实现了ApplicationListener接口，当一个ApplicationEvent 被发布以后，bean会自动被通知。
 */
@Data
public class MyEvent extends ApplicationEvent {

    private String msg;

    public MyEvent(Object source, String message){
        super(source);
        this.msg=message;
    }
}


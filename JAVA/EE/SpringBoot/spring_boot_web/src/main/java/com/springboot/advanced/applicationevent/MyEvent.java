package com.springboot.advanced.applicationevent;

import org.springframework.context.ApplicationEvent;

// 定义事件对象，封装事件要携带的信息
public class MyEvent extends ApplicationEvent {

    private String msg;

    public MyEvent(Object source, String message){
        super(source);
        this.msg=message;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "msg='" + msg + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}


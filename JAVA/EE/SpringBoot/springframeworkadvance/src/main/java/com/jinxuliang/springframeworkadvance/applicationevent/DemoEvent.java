package com.jinxuliang.springframeworkadvance.applicationevent;

import org.springframework.context.ApplicationEvent;

//定义事件对象，封装事件要携带的信息
public class DemoEvent extends ApplicationEvent {
    private String msg;

    public DemoEvent(Object source,String message){
        super(source);
        this.msg=message;
    }

    @Override
    public String toString() {
        return "DemoEvent{" +
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


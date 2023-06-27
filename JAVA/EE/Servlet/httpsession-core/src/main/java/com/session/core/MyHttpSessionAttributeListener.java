package com.session.core;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener
public class MyHttpSessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("session中添加属性: " + event.getName() + " = " + event.getValue());
        System.out.println("session:KKKKKKKK******** " + event.getSession());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        System.out.println("session中的" + event.getName() + "属性被移除了,值为: " + event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("session中替换 " + event.getName() + "属性,旧值:" + event.getValue() +
                ", 新值: " + event.getSession().getAttribute(event.getName()));
    }
}

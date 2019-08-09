package com.listener.core.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

// 用于在初始化和销毁Request时记录IP地址。
// 1. 每一次请求Tomcat都会创建一个Request，它的创建会被ServletRequestListener监听到
// 2. 请求结束，Request销毁，被监听到
@WebListener
public class MyServletRequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        System.out.println("ServletRequest initialized. Remote IP="+servletRequest.getRemoteAddr());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    	ServletRequest servletRequest = servletRequestEvent.getServletRequest();
    	System.out.println("ServletRequest destroyed. Remote IP="+servletRequest.getRemoteAddr());
    }

}

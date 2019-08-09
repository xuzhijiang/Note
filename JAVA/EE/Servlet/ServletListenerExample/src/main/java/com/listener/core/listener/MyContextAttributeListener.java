package com.listener.core.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

// 在servlet context中添加，删除或替换属性时打印事件的简单实现。
// 1. get/setAttribute()时，会触发属性监听。
public class MyContextAttributeListener implements ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
    	System.out.println("ServletContext attribute added::{"+servletContextAttributeEvent.getName()+"-----"+servletContextAttributeEvent.getValue()+"}");
    }

    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
    	System.out.println("ServletContext attribute replaced::{"+servletContextAttributeEvent.getName()+"------"+servletContextAttributeEvent.getValue()+"}");
    }

    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
    	System.out.println("ServletContext attribute removed::{"+servletContextAttributeEvent.getName()+"------"+servletContextAttributeEvent.getValue()+"}");
    }
	
}

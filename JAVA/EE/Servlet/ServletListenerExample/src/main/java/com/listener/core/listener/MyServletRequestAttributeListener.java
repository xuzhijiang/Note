package com.listener.core.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestAttributeListener implements ServletRequestAttributeListener {

    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {
        System.out.println("MyServletRequestAttributeListener add");
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {

        System.out.println("MyServletRequestAttributeListener remove");
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {
        System.out.println("MyServletRequestAttributeListener replaced");
    }
}

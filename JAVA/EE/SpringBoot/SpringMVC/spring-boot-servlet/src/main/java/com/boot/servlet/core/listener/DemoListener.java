package com.boot.servlet.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("DemoListener监听ServletContext的生命周期: contextInitialized called!!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("DemoListener监听ServletContext的生命周期: contextDestroyed called!!");
    }
}

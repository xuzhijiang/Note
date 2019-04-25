package com.springboot.core.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("==>DemoListener启动: Context initialize...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}

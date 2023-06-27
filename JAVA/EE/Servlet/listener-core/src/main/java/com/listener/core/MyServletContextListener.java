package com.listener.core;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

	// 监听ServletContext对象创建,在项目启动时会回调
	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext servletContext = servletContextEvent.getServletContext();
		System.out.println("项目路径: " + servletContext.getContextPath());
    	String path = servletContext.getInitParameter("AppPath");
		System.out.println("ServerPath: " + path);
		servletContext.setAttribute("serverPath", path);
    	System.out.println("ServletContext initialized......" + servletContext.getClass());
    }

    // 项目关停时会回调
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();
		String path = (String) ctx.getAttribute("serverPath");
		System.out.println("ServletContext destroy.....");
		System.out.println("********ServerPath: " + path);
    }
}

package com.listener.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.listener.core.db.DBConnectionManager;

// 监听ServletContext的创建和销毁.

// 1. 在项目启动时ServletContextListener监听到ServletContext对象创建
// 2. 项目关停，ServletContext销毁，被监听到
@WebListener
public class MyServletContextListener implements ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext servletContext = servletContextEvent.getServletContext();
    	
    	String url = servletContext.getInitParameter("DBURL");
    	String u = servletContext.getInitParameter("DBUSER");
    	String p = servletContext.getInitParameter("DBPWD");
    	
    	//create database connection from init parameters and set it to context
    	DBConnectionManager dbManager = new DBConnectionManager(url, u, p);
    	servletContext.setAttribute("DBManager", dbManager);
    	System.out.println("ServletContext initialized......");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	ServletContext ctx = servletContextEvent.getServletContext();

    	DBConnectionManager dbManager = (DBConnectionManager) ctx.getAttribute("DBManager");
    	dbManager.closeConnection();

    	System.out.println("ServletContext destroy.....");
    }
	
}

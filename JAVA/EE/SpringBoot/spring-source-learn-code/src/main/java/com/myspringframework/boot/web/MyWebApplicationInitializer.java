package com.myspringframework.boot.web;

import com.myspringframework.boot.config.MySpringBootConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

// WebApplicationInitializer是spring的,当tomcat启动的时候,怎么就调用到WebApplicationInitializer的onStartup()方法了呢?
// 我tomcat是一个标准,你spring也是一个标准,我tomcat这么牛逼的一个公司,为什么要依赖你的spring的东西呢?为什么要做一个舔狗去依赖你spring啊?
// 我tomcat要启动,凭什么要依赖你的spring的东西啊? 我tomcat为什么要依赖的spring的jar呢?
//
// 这就引出了spi机制

// Tomcat.start()之后,为什么会调用到MyWebApplicationInitializer.onStartup()方法?
// 看com.spi.MainClass中的解释
public class MyWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.out.println("初始化MyWebApplicationInitializer........");

        // 对应web.xml中的org.springframework.web.context.ContextLoaderListener,用于启动父容器
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MySpringBootConfig.class);
        // 这里有一个bug,加上refresh()之后,再搭配com.myspringframework.boot.config.MySpringBootConfig中的
        // @EnableWebMvc,tomcat启动就会报错,所以这里注释掉了
        //context.refresh();

        // 注册DispatcherServlet
        // 对应web.xml中初始化DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context); // 传入spring的上下文
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}

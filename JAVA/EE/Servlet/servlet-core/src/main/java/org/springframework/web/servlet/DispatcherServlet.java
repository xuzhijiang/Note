package org.springframework.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 模仿spring-webmvc中的DispatcherServlet
public class DispatcherServlet extends HttpServlet {

    public static final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 通过 ServletConfig 得到SpringMVC配置文件的位置
        ServletConfig servletConfig = getServletConfig();
        String contextConfigLocation = servletConfig.getInitParameter(CONTEXT_CONFIG_LOCATION);
        System.out.println("有请求来了: " + contextConfigLocation);
        // 2. dom4j解析springmvc.xml文件,反射创建bean,存入SpringMVC的IOC容器(子容器)
        // 3. 解析当前请求的url,从SpringMVC的IOC容器中找出匹配的Bean处理这个请求.
    }

}

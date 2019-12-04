package spring.source.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@FunctionalInterface
public interface ServletContextInitializer {

    // ServletContextInitializer表示Servlet初始化器，用于设置ServletContext中的一些配置，
    // 在使用ServletWebServerFactory接口的getWebServer方法获取Servlet内置容器并且
    // 容器启动的时候调用onStartup方法：
    void onStartup(ServletContext servletContext) throws ServletException;

}

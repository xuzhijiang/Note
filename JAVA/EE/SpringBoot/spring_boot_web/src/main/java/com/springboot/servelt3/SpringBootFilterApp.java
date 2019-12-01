package com.springboot.servelt3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 引入@ServletComponentScan的目的就是为了支持一以下Servlet 3.0 注解: @WebFilter, @WebListener, @WebServlet,
// @ServletComponentScan
@ServletComponentScan(basePackages = "com.springboot.servelt3")
// @ServletComponentScan(basePackageClasses = {HelloFilter.class, HelloServlet.class, MyServletContextListener.class})
public class SpringBootFilterApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootFilterApp.class, args);
    }

}

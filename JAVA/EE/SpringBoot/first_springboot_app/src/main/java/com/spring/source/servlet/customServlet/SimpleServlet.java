package com.spring.source.servlet.customServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 2.@ServletComponentScan注解和@WebServlet、@WebFilter以及@WebListener注解配合使用。
 * @ServletComponentScan注解启用ImportServletComponentScanRegistrar类，
 * 是个ImportBeanDefinitionRegistrar接口的实现类，会被Spring容器所解析。
 * ServletComponentScanRegistrar内部会解析@ServletComponentScan注解，
 * 然后会在Spring容器中注册ServletComponentRegisteringPostProcessor，
 * 是个BeanFactoryPostProcessor，会去解析扫描出来的类是不是有@WebServlet、@WebListener、@WebFilter这3种
 * 注解，有的话把这3种类型的类转换成ServletRegistrationBean、FilterRegistrationBean或者
 * ServletListenerRegistrationBean，然后让Spring容器去解析：
 */
@WebServlet(urlPatterns = "/simple")
public class SimpleServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("receive by SimpleServlet");
    }

}
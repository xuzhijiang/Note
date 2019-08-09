package com.servlet.core;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 直接实现Servlet接口，定义"原生的"Servlet
 */
@WebServlet(urlPatterns = {"/primitive-servlet-url"})
public class PrimitiveServlet implements Servlet {

    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
        String servletName = servletConfig.getServletName();// 获取WebServlet注解中的name
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.print("<html><head></head>"
                + "<body>Hello from " + servletName
                + "</body></html>");
        // 向浏览器返回一个html文档
    }

    @Override
    public String getServletInfo() {
        return "My First Servlet";
    }

    @Override
    public void destroy() {
        System.out.println("My First Servlet Destoryed.");
    }
}
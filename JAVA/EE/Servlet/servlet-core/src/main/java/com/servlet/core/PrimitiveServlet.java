package com.servlet.core;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 直接实现Servlet接口，定义"原生的"Servlet
 *
 * Servlet的init、service、destroy是生命周期方法。init和destroy各自只执行一次，即servlet创建和销毁时。
 * 而service会在每次有新请求到来时被调用。也就是说，我们主要的业务代码需要写在service中。
 */
@WebServlet(urlPatterns = {"/primitive-servlet-url"}, name = "hahahah-Primitive")
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
        // 获取WebServlet注解中的name
        String servletName = servletConfig.getServletName();

        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.print("<html><head></head>"
                + "<body>Hello from " + servletName
                + "</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "Primitive info";
    }

    @Override
    public void destroy() {
        System.out.println("Primitive Destoryed.");
    }
}
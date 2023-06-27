package com.servlet.core;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/generic"},
        initParams = {
                @WebInitParam(name = "admin", value = "admin-generic-servlet"),
                @WebInitParam(name = "email", value = "admin@example.com")
        }
)
public class GenericServletSubClass extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        // 获取初始化此Servlet的配置，
        ServletConfig servletConfig = getServletConfig();
        // 获取初始化此Servlet的参数
        String admin = servletConfig.getInitParameter("admin");
        String email = servletConfig.getInitParameter("email");
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head></head><body>" + "Admin:" + admin + "<br/>Email:" + email + "</body></html>");
    }
}

package cn.edu.bit.cs.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 直接实现Servlet接口，定义"原生的"Servlet
 */
@WebServlet(name = "MyFirstServlet",urlPatterns = {"/myfirst"})
public class MyFirstServlet implements Servlet {
    
    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig)
            throws ServletException {
        this.servletConfig = servletConfig;
        System.out.println("MyFirst Servlet initialized.");
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    // 在service方法中生成发回给浏览器的HTML代码
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse)
            throws ServletException, IOException {
        String servletName = servletConfig.getServletName();
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.print("<html><head></head>"
                + "<body>Hello from " + servletName
                + "</body></html>");
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

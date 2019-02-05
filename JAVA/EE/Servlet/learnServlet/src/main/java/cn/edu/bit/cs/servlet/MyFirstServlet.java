package cn.edu.bit.cs.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 直接实现Servlet接口，定义"原生的"Servlet
 */
// urlPatterns = {"/myfirst"})中的myfirst就是指定的URL
@WebServlet(name = "MyFirstServlet-Test", urlPatterns = {"/myfirst"})
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
        // 访问localhost:8080/myfirst，可以看到浏览器显示了Servlet输出的信息"Hello from servlet"

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
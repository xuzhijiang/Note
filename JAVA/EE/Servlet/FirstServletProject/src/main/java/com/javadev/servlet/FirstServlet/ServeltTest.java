package com.javadev.servlet.FirstServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 注意我把war打包成XzjMyFirstServlet.war,然后在tomcat的
// webapps下，自动解压为XzjMyFirstServlet，所以我访问
// http://localhost:8080/XzjMyFirstServlet, 会返回给浏览器IDEA项目目录webapp
// 下的index.jsp

// 我们访问http://localhost:8080/XzjMyFirstServlet/myfirst,会返回ServeltTest中doGet方法中
// 的writer内容
@WebServlet(name = "MyFirstServlet", urlPatterns = {"/myfirst"})
public class ServeltTest extends HttpServlet {
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.print("<html><head></head>"
                + "<body>Hello from servletTest doGet!"
                + "</body></html>");
        writer.close();
    }
}
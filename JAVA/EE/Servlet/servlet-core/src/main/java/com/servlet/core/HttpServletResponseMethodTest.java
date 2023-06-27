package com.servlet.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpServletResponseMethodTest extends HttpServlet {
    /**
     * @param request
     * @param response 至于Response，Tomcat传给Servlet时，它还是空的对象。Servlet逻辑处理后得到结果，
     *                 最终通过response.write()方法，将结果写入response内部的缓冲区。
     *                 Tomcat会在servlet处理结束后，拿到response，遍历里面的信息，组装成HTTP响应发给客户端。
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 在默认情况下，JSP编译器会将JSP页面的内容类型设为text/html。如果要使
        // 用不同的类型，则需要通过调用response.setContentType()或者使用页面指令来设置内容类型.
        // response.setContentType("text/json");
        response.setContentType("text/html");
        //            response.setStatus();
        //            response.setHeader();
        /**
         * 默认发送状态码302
         */
//        response.sendRedirect("LoginSuccess.jsp"); // 原生的重定向

        PrintWriter writer = response.getWriter();

        String value = getServletConfig().getInitParameter("name1");

        writer.print("<html><head></head>" + "<body>" + value + "</body></html>");
    }
}

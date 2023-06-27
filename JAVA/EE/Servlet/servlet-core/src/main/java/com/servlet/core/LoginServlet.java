package com.servlet.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 1. 在Servlet 3之前，我们需要在web.xml中配置Servlet,但servlet 3.0可以使用注解代替.
// 3. @WebServlet注解的value是urlPatterns的别名.
@WebServlet(urlPatterns = { "/login" },
        initParams = { @WebInitParam(name = "user", value = "xzj"),
                       @WebInitParam(name = "pwd", value = "xzj-pwd") })
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        /**
         * 读取 web.xml中 context-param 配置的值.
         */
        // getServletContext获取ServletContext(Servlet上下文)
        // 然后根据ServletContext的上下文,获取web.xml中context-param元素的属性值
        if (getServletContext().getInitParameter("secret").equals("xxx")) {
            getServletContext().setAttribute("DB_Success", "True");
        } else {
            throw new ServletException("DB Connection error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        // 遍历所有传上来的参数
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            System.out.println("**********key: " + key + "value: " + request.getParameter(key));
        }

        /**
         * 获取 web.xml 中 servlet标签中的 init-param 参数
         * getServletConfig() 返回 ServletConfig, 每一个Servlet都有自己的 ServletConfig
         */
        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("pwd");

        if (userID.equals(user) && password.equals(pwd)) {
            /**
             * 默认发送状态码302
             */
            response.sendRedirect("index.jsp"); // 原生的重定向
        } else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.println("<font color=red>Either user name or password is wrong.</font>");
            rd.include(request, response);
        }
    }

}

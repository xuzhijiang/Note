package com.jinxuliang.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

// 创建一个log.txt文件，记录每次的HTTP请求
@WebFilter(filterName = "LoggingFilter", urlPatterns = { "/*"},
        asyncSupported = true,
        initParams = {
                @WebInitParam(name = "logFileName", value = "log.txt"),
                @WebInitParam(name = "prefix", value = "URI: ") })
public class LoggingFilter implements Filter {

    private PrintWriter logger;
    private String prefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix = filterConfig.getInitParameter("prefix");
        String logFileName = filterConfig.getInitParameter("logFileName");
        String appPath = filterConfig.getServletContext().getRealPath("/");
        System.out.println(appPath);
        // without path info in logFileName, the log file will be
        // created in $TOMCAT_HOME/bin
        System.out.println("logFileName:" + logFileName);
        try {
            logger = new PrintWriter(new File(appPath,  logFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("LoggingFilter.doFilter");
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            logger.println(new Date() + " " + prefix + httpServletRequest.getRequestURI());
            logger.flush();
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("destroying filter");
        if (logger != null) {
            logger.close();
        }
    }
}

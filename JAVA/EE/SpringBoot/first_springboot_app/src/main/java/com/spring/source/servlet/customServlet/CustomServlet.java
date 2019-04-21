package com.spring.source.servlet.customServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringBoot默认只会添加一个Servlet，也就是DispatcherServlet，
 * 如果我们想添加自定义的Servlet或者是Filter还是Listener，有以下几种方法。
 *
 * 1.在Spring容器中声明ServletRegistrationBean、FilterRegistrationBean或者
 * ServletListenerRegistrationBean。原理在DispatcherServlet的构造章节中已经说明
 */
@Configuration
public class CustomServlet {

    @Bean
    public ServletRegistrationBean customServlet() {
        return new ServletRegistrationBean(new MyCustomServlet(), "/custom");
    }

    private static class MyCustomServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.getWriter().write("receive by custom servlet");
        }
    }

    /**
     * 3.在Spring容器中声明Servlet、Filter或者Listener。
     * 因为在ServletContextInitializerBeans内部会去调用addAdaptableBeans方法把
     * 它们包装成ServletRegistrationBean：
     * @return
     */
    @Bean(name = "dispatcherServlet")
    public DispatcherServlet myDispatcherServlet() {
        return new DispatcherServlet();
    }
}

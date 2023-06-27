package com.boot.servlet.core.config;

import com.boot.servlet.core.filter.DemoFilter;
import com.boot.servlet.core.listener.DemoListener;
import com.boot.servlet.core.servlet.DemoServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

//注册三大组件
@Configuration
public class ServletConfig {

    // 注意: 因为这个filter配置的是拦截/*, 所以也会拦截这个静态资源的请求: http://localhost:8080/favicon.ico
    @Bean
    public FilterRegistrationBean getDemoFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DemoFilter());
        List<String> urlPatterns = new ArrayList<>();
        // 配置过滤器拦截路径，可以添加多个
        urlPatterns.add("/*");
        // 设置demoFilter过滤的url模式
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        //filterRegistrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean getDemoServlet() {
        DemoServlet demoServlet = new DemoServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(demoServlet);
        List<String> urlMappings = new ArrayList<>();
        urlMappings.add("/demoservlet");// 访问路径，可以添加多个
        servletRegistrationBean.setUrlMappings(urlMappings);
        servletRegistrationBean.setLoadOnStartup(1);
        return servletRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new DemoListener());
        // servletListenerRegistrationBean.setOrder(1);
        return servletListenerRegistrationBean;
    }

}

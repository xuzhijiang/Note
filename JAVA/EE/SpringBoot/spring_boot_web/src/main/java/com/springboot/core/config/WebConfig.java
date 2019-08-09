package com.springboot.core.config;

import com.springboot.core.web.filter.DemoFilter;
import com.springboot.core.web.listener.DemoListener;
import com.springboot.core.web.servlet.DemoServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.List;
import java.util.ArrayList;
import java.util.EventListener;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FilterRegistrationBean getDemoFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new DemoFilter());
        List<String> urlPatterns = new ArrayList<>();
        // 拦截路径，可以添加多个
        urlPatterns.add("/*");
        // 设置demoFilter过滤的url模式
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean getDemoServlet() {
        DemoServlet demoServlet = new DemoServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(demoServlet);
        List<String> urlMappings = new ArrayList<String>();
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

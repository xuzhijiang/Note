package com.funtl.hello.dubbo.service.user.consumer.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 每个项目都要监控,所以每个项目都要添加这个servlet
@Configuration
public class HystrixDashboardConfiguration {

    @Bean
    public ServletRegistrationBean getHystrixServelet() {
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> hystrixMetricsStreamServletServletRegistrationBean = new ServletRegistrationBean<>(hystrixMetricsStreamServlet);

        hystrixMetricsStreamServletServletRegistrationBean.setLoadOnStartup(1);
        hystrixMetricsStreamServletServletRegistrationBean.addUrlMappings("/hystrix.stream");
        hystrixMetricsStreamServletServletRegistrationBean.setName("hystrixMetricsStreamServlet");

        return hystrixMetricsStreamServletServletRegistrationBean;
    }
}

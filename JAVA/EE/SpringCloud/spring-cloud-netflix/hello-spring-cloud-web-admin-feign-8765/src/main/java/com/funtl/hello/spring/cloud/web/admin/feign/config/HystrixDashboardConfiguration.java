package com.funtl.hello.spring.cloud.web.admin.feign.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixDashboardConfiguration {

    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

    /**
     * 测试 Hystrix Dashboard:
     *
     * 浏览器端访问 http://localhost:8765/hystrix,
     * 在Hystrix Dashboard输入框中输入:http://localhost:8765/hystrix.stream,
     * 输入Title: WebAdminFeign
     *
     * 然后点击 Monitor Stream，进入下一个界面，
     * 访问 http://localhost:8765/hi?message=HelloFeign 此时会出现监控界面：
     */
}

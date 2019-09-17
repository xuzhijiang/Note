package com.funtl.hello.spring.cloud.web.admin.feign.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class HystrixDashboardConfiguration {

    /**
     * Spring Boot 2.x 版本开启 Hystrix Dashboard 与 Spring Boot 1.x 的方式略有不同，
     * 需要增加一个 HystrixMetricsStreamServlet 的配置
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        // 注意这个是用在查看熔断器监控状态的,输入http://host:port/hystrix
        // 然后在文本框中输入: http://host:port/hystrix.stream就可以跳转到相应的监控的情况.
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}

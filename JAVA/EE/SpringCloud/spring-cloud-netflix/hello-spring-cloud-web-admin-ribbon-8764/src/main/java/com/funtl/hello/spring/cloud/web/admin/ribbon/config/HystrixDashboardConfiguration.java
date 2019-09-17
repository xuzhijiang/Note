package com.funtl.hello.spring.cloud.web.admin.ribbon.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 测试 Hystrix Dashboard(熔断器仪表盘):
 *
 * 浏览器端访问 http://localhost:8764/hystrix,
 * 在Hystrix Dashboard输入框中输入:http://localhost:8764/hystrix.stream,
 * 输入Title: WebAdminRibbon
 *
 * 然后点击 Monitor Stream，进入下一个界面，
 * 访问 http://localhost:8764/hi?message=HelloRibbon 此时会出现监控界面：
 */
@Configuration
public class HystrixDashboardConfiguration {

    /**
     * Spring Boot 2.x 版本开启 Hystrix Dashboard 与 Spring Boot 1.x 的方式略有不同，
     * 需要增加一个 HystrixMetricsStreamServlet 的Servlet配置，代码如下：
     */
    @Bean
    public ServletRegistrationBean getServlet() {
        // metrics: 指标
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}

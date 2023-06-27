package com.springdata.jdbc.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认情况下spring-boot-starter-jdbc使用的是:
 *  HikariDataSource(这个依赖spring-boot-starter-jdbc帮我们自动导入了) 这个 (DataSource Type)数据源类型
 *
 * 一旦我们配置了自己的DataSource,默认的就不会生效了.
 */
@Configuration
public class DruidConfig {

    @Bean
    // DruidDataSource自己特有的属性没有被包含在DataSourceProperties中,所以yml中
    // 定义的DruidDataSource特有的属性默认不会生效,所以需要我们在这里配置.使它们生效.
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid() {
        return new DruidDataSource();
    }

    // 配置Druid的监控

    //1、注册一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        // 注册servelt拦截的路径为/druid/下的所有请求.
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();

        // 这些参数在StatViewServlet都有定义
        initParams.put("loginUsername","admin"); // 访问用户
        initParams.put("loginPassword","123456"); // 访问密码
        initParams.put("allow","");//默认就是允许所有访问
//        initParams.put("allow","localhost");//只允许localhost访问
        initParams.put("deny","192.168.15.21"); // 拒绝这个ip访问.

        bean.setInitParameters(initParams);
        return bean;
    }


    //2、配置一个web监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*"); // 不对哪些url进行过滤

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Arrays.asList("/*")); // 配置要过滤的url
        return  bean;
    }
}
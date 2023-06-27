package org.mq.core.meituan.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

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
}
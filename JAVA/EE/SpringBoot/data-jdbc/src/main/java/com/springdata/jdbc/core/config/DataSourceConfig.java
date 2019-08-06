package com.springdata.jdbc.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * 本例使用的是内存数据库,所以此类无用,暂时注释掉关键部分.
 * 使用的时候,需要这样创建JdbcTemplate:
 *
 * jdbcTemplate = new JdbcTemplate(dataSource);
 *
 * 将本类中的DataSource设置到JdbcTemplate中.
 */
@Configuration
@PropertySource("classpath:application.yml")
public class DataSourceConfig {

    @Autowired
    Environment environment;

    //@Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(environment.getProperty("url"));
        driverManagerDataSource.setUsername(environment.getProperty("username"));
        driverManagerDataSource.setPassword(environment.getProperty("password"));
        driverManagerDataSource.setDriverClassName(environment.getProperty("driverClassName"));
        return driverManagerDataSource;
    }
}
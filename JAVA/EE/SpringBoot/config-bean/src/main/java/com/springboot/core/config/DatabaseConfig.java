package com.springboot.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
// @MapperScan是mybatis-spring提供的一个注解，指定Mapper类所在的包
@MapperScan(basePackages = "com.springboot.core.mapper")
public class DatabaseConfig {

    // 我们只需要配置一个数据源即可，mybatis-spring-boot-starter会自动帮我们创建SqlSessionFactory和SqlSessionTemplate
    @Bean
    public DataSource druidDataSoruce() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("password");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return druidDataSource;
    }

}

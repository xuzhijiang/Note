package com.spring.multidatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary//用@Primary区分主数据源。
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties primaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Create primary (default) DataSource.
     */
    @Bean
    @Primary
    public DataSource primaryDataSource(@Autowired DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean("secondDataSourceProperties")
    @ConfigurationProperties("spring.second-datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Create second DataSource and named "secondDatasource".
     */
    @Bean("secondDatasource")
    public DataSource secondDataSource(@Autowired @Qualifier("secondDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

}

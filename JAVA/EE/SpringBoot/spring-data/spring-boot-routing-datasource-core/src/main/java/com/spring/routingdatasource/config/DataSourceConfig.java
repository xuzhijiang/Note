package com.spring.routingdatasource.config;

import com.spring.routingdatasource.context.RoutingDataSource;
import com.spring.routingdatasource.context.RoutingDataSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean
    public PlatformTransactionManager txManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Create primary (default) routing DataSource.
     */
    @Bean
    @Primary
    public DataSource primaryDataSource(@Autowired @Qualifier("rwDatasource") DataSource rwDatasource,
                                        @Autowired @Qualifier("roDatasource") DataSource roDatasource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(RoutingDataSourceContext.MASTER, rwDatasource);
        targetDataSources.put(RoutingDataSourceContext.SLAVE, roDatasource);

        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(rwDatasource);

        return dataSource;
    }

    @Bean("rwDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties rwDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Create rw(读写) DataSource and named "rwDatasource".
     */
    @Bean("rwDatasource")
    public DataSource rwDataSource(@Autowired @Qualifier("rwDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

    @Bean("roDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.ro-datasource")
    public DataSourceProperties roDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Create ro(只读) DataSource and named "roDatasource".
     */
    @Bean("roDatasource")
    public DataSource roDataSource(@Autowired @Qualifier("roDataSourceProperties") DataSourceProperties props) {
        return props.initializeDataSourceBuilder().build();
    }

}

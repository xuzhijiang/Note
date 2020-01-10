package com.springdata.jdbc.multidatasource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;

@Configuration
public class JDBCTemplateConfig {

    /**
     * Create primary (default) JdbcTemplate from primary DataSource.
     */
    @Bean
    @Primary
    public JdbcTemplate primaryJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Create second JdbcTemplate from second DataSource.
     * 用第二个数据源创建第二个JdbcTemplate
     */
    @Bean(name = "secondJdbcTemplate")//第二个数据源的JdbcTemplate
    public JdbcTemplate secondJdbcTemplate(@Autowired @Qualifier("secondDatasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.regex("^/api/.*$")).build();
    }

}

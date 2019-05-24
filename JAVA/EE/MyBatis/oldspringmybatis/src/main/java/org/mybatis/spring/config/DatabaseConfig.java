package org.mybatis.spring.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

//整合七：@MapperScan
//    如果读者习惯使用注解，而不是xml文件的方式进行配置，mybatis-spring提供了@MapperScan注解，其用于取代MapperScannerConfigurer。以下演示了如何通过注解的方式来配置mybatis。

@Configuration
@MapperScan(basePackages = "com.tianshouzhi.security.mapper",//等价于basePackage属性
        //markerInterface = MybatisMapperInterface.class,//等价于markerInterface属性，默认为null
        //annotationClass = MybatisMapper.class,//等价于annotationClass属性，默认为null
        sqlSessionFactoryRef = "sqlSessionFactory")//等价于sqlSessionFactoryBeanName属性，默认为null
public class DatabaseConfig {

    //定义数据源
    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUsername("root");
                dataSource.setPassword("password");
                        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis?characterEncoding=UTF-8");
        dataSource.setDriverClass(com.mysql.jdbc.Driver.class);
        return dataSource;
    }

    //定义SqlSessionFactoryBean
    @Autowired
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        return ssfb;
    }
}
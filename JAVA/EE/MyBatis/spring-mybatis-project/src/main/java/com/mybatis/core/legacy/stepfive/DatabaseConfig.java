package com.mybatis.core.legacy.stepfive;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

// 如果读者习惯使用注解，而不是xml文件的方式进行配置，mybatis-spring提供了@MapperScan注解，
// 其用于取代MapperScannerConfigurer。
@Configuration
@MapperScan(basePackages = "com.mybatis.core.legacy.stepfive.mapper",//等价于basePackage属性,basePackage 属性是让指定映射器接口的包路径
        //markerInterface = MybatisMapperInterface.class,//等价于markerInterface属性，默认为null
        //annotationClass = MybatisMapper.class,//等价于annotationClass属性，默认为null
        sqlSessionFactoryRef = "sqlSessionFactory")//等价于sqlSessionFactoryBeanName属性，默认为null
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        return ssfb;
    }

}
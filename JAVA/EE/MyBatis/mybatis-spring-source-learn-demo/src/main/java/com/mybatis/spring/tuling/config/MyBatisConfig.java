package com.mybatis.spring.tuling.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
// 问: 为什么添加@MapperScan,能够替代xml中org.mybatis.spring.mapper.MapperScannerConfigurer这个bean的配置呢?
@MapperScan(basePackages = {"com.mybatis.spring.tuling.mapper"}
        //,markerInterface = MybatisMapperInterface.class,//等价于markerInterface属性，默认为null
        //annotationClass = MybatisMapper.class,//等价于annotationClass属性，默认为null
        //sqlSessionFactoryRef = "sqlSessionFactory" //告诉mybatis使用哪个SqlSessionFactory，默认为null
)
@ComponentScan(basePackages = {"com.mybatis.spring.tuling"})
@EnableTransactionManagement
public class MyBatisConfig {

    // 注意这个bean的名字就是方法的名字sqlSessionFactory
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        // 设置 MyBatis 配置文件路径
        factoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        // 设置 mapper 映射文件路径
        // factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mybatis/mapper/*.xml"));
        factoryBean.setTypeAliasesPackage("com.mybatis.spring.tuling.entity");

        // 指定别名支持类型
        // factoryBean.setTypeAliasesSuperType(Employee.class);
        // factoryBean.setTypeAliases(Dept.class);
        return factoryBean;
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydb?characterEncoding=UTF-8");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        return transactionManager;
    }
}

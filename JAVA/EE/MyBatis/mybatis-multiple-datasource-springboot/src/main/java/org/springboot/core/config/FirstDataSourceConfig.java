package org.springboot.core.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class FirstDataSourceConfig {

    /**
     * 创建数据源
     * @return
     */
    @Bean(name = "first_data_source") // 给这个DataSource bean起一个名字用于识别
    @Primary// @Primary 注解声明为默认数据源
    @ConfigurationProperties(prefix = "spring.datasource.hikari.db1")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "first_sql_session_factory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("first_data_source") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean(name = "first_sql_session_template")
    @Primary
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("first_sql_session_factory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置事务管理器
     * @return
     */
    @Bean(name = "first_data_source_transaction_manager")
    @Primary
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("first_data_source") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

package com.di.config;

import com.di.condition.MongoDBDatabaseTypeCondition;
import com.di.condition.MySQLDatabaseTypeCondition;
import com.di.dao.UserDAO;
import com.di.dao.impl.JdbcUserDAO;
import com.di.dao.impl.MongoUserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

// 指定不同的条件，实例化不同的数据存取组件
@Configuration
public class DBConfig {

    @Bean
    @Conditional(MySQLDatabaseTypeCondition.class)
    public UserDAO jdbcUserDAO(){
        return new JdbcUserDAO();
    }

    @Bean
    @Conditional(MongoDBDatabaseTypeCondition.class)
    public UserDAO mongoUserDAO(){
        return new MongoUserDAO();
    }

}
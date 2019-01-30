package com.jinxuliang.autoconfig.config;

import com.jinxuliang.autoconfig.condition.MongoDBDatabaseTypeCondition;
import com.jinxuliang.autoconfig.condition.MySQLDatabaseTypeCondition;
import com.jinxuliang.autoconfig.dao.JdbcUserDAO;
import com.jinxuliang.autoconfig.dao.MongoUserDAO;
import com.jinxuliang.autoconfig.dao.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//指定不同的条件，实例化不同的数据存取组件
@Configuration
public class AppConfig {
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


package com.spring.condition.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {
    // 根据具体的条件，实例化对应的bean.
    @Bean
    @Conditional(MySQLCondition.class)
    public UserDAO jdbcUserDAO(){
        return new JdbcUserDAO();
    }

    @Bean
    @Conditional(MongoDBCondition.class)
    public UserDAO mongoUserDAO(){
        return new MongoUserDAO();
    }
}

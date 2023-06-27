package org.mybatis.core.springboot.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

// 自定义MyBatis的配置规则；给容器中添加一个ConfigurationCustomizer
@org.springframework.context.annotation.Configuration
public class MybatisConfig {

// 开启驼峰 配置
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(Configuration configuration) {
//                configuration.setMapUnderscoreToCamelCase(true);
//            }
//        };
//    }
}

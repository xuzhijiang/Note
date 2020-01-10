package com.spring.bean.scope.core.WebApplicationContext;

import com.spring.bean.scope.core.WebApplicationContext.InstB;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MainConfig {
    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public InstB getPrototypeBean() {
        return new InstB();
    }
}

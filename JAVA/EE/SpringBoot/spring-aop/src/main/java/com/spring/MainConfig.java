package com.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = "com.spring")
@Configuration
@EnableAspectJAutoProxy
public class MainConfig {
    @Bean
    public AspectA aspectA() {
        return new AspectA();
    }

    @Bean
    public AspectB aspectB() { return new AspectB(); }

    @Bean
    public AspectC aspectC() { return new AspectC(); }
}

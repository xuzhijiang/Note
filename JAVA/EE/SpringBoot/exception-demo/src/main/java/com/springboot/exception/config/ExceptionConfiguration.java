package com.springboot.exception.config;

import com.springboot.exception.resolver.ExampleSimpleMappingExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionConfiguration {

    @Bean
    public ExampleSimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        ExampleSimpleMappingExceptionResolver r = new ExampleSimpleMappingExceptionResolver();
        r.setDefaultErrorView("default_error_view");
        return r;
    }
}

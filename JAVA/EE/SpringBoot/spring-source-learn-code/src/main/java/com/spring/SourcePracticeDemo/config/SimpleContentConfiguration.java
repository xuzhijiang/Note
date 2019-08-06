package com.spring.SourcePracticeDemo.config;

import com.spring.SourcePracticeDemo.service.ContentService;
import com.spring.SourcePracticeDemo.service.impl.SimpleContentService;
import org.springframework.context.annotation.Bean;

public class SimpleContentConfiguration {

    @Bean
    public ContentService contentService(){
        return  new SimpleContentService();
    }
}

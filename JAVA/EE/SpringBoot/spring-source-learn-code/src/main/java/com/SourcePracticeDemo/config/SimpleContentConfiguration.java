package com.SourcePracticeDemo.config;

import com.SourcePracticeDemo.service.ContentService;
import com.SourcePracticeDemo.service.impl.SimpleContentService;
import org.springframework.context.annotation.Bean;

public class SimpleContentConfiguration {

    @Bean
    public ContentService contentService(){
        return  new SimpleContentService();
    }
}

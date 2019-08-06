package com.spring.SourcePracticeDemo.config;

import com.spring.SourcePracticeDemo.service.ContentService;
import com.spring.SourcePracticeDemo.service.impl.CoreContentService;
import org.springframework.context.annotation.Bean;

public class CoreContentConfiguration {
    @Bean
    public ContentService contentService(){
        return new CoreContentService();
    }

}

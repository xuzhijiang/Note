package com.SourcePracticeDemo.config;

import com.SourcePracticeDemo.service.ContentService;
import com.SourcePracticeDemo.service.impl.CoreContentService;
import org.springframework.context.annotation.Bean;

public class CoreContentConfiguration {
    @Bean
    public ContentService contentService(){
        return new CoreContentService();
    }

}

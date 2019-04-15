package com.jinxuliang.spring_mvc_controller_demo.annotation;

import org.springframework.context.annotation.Bean;

public class CoreContentConfiguration {
    @Bean
    public ContentService contentService(){
        return new CoreContentService();
    }

}

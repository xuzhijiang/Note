package com.jinxulaing.webjar_demo.controllers;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

// 映射网页小图标，在静态资源文件夹下找
@Configuration
@ConditionalOnProperty(
        value = {"spring.mvc.favicon.enabled"},
        matchIfMissing = true
)
public class FaviconConfiguration implements ResourceLoaderAware {

    //private final ResourceProperties resourceProperties;
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }

    /**
     * 欢迎页面的映射
     * @param applicationContext
     * @return
     */
//    @Bean
//    public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext applicationContext) {
//        return new WelcomePageHandlerMapping(new TemplateAvailabilityProviders(applicationContext), applicationContext, this.getWelcomePage(), this.mvcProperties.getStaticPathPattern());
//    }
}

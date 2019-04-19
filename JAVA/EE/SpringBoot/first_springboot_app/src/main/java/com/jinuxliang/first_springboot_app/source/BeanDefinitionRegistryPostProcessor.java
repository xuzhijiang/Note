package com.jinuxliang.first_springboot_app.source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

// 继承BeanFactoryPostProcessor，作用跟BeanFactoryPostProcessor一样，
// 只不过是使用BeanDefinitionRegistry对bean进行处理
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

    /**
     * Modify the application context's internal bean definition registry after its
     * standard initialization. All regular bean definitions will have been loaded,
     * but no beans will have been instantiated yet. This allows for adding further
     * bean definitions before the next post-processing phase kicks in.
     * @param registry the bean definition registry used by the application context
     * @throws org.springframework.beans.BeansException in case of errors
     */
    void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

}
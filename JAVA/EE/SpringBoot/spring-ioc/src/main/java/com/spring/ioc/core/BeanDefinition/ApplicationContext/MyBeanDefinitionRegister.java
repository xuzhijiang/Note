package com.spring.ioc.core.BeanDefinition.ApplicationContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

// 实现BeanDefinitionRegistryPostProcessor接口的postProcessBeanDefinitionRegistry方法也可以把Bean定义导入到IoC容器中
@Component
public class MyBeanDefinitionRegister implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 导入F的bean定义
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(InstF.class);
        registry.registerBeanDefinition("instF", rootBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {}
}

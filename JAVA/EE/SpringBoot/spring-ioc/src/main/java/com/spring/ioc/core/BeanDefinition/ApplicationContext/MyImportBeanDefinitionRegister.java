package com.spring.ioc.core.BeanDefinition.ApplicationContext;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

// ImportBeanDefinitionRegistrar(导入BeanDefinition的注册器): 可以把BeanDefinition导入到BeanDefinitionMap中
// 这种玩法只能导入一个BeanDefinition
public class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 把InstC的BeanDefinition导入到BeanDefinitionMap中
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(InstC.class);
        registry.registerBeanDefinition("instC", rootBeanDefinition);
    }
}

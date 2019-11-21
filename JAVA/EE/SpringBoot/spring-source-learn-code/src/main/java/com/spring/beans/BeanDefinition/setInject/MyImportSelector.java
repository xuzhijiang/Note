package com.spring.beans.BeanDefinition.setInject;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

// 可以通过@Import往BeanDefinitionMap中放BeanDefinition
public class MyImportSelector implements ImportSelector {
    // 返回值就是我们要导入到BeanDefinitionMap中的BeanDefinition所对应的class的"全路径"
    // 这个就是SpringBoot自动装配原理的核心
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 可以批量加载(可以写多个)
        // 唯独这种方法可以匹配加载
        return new String[]{"com.spring.beans.BeanDefinition.setInject.InstB"};
    }
}

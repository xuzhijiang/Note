package com.jinxuliang.spring_mvc_controller_demo.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

// 这个ContentImportSelector根据EnableContentService注解里的policy加载不同的bean
public class ContentImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Class<?> annotationType = EnableContentService.class;
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false));
        String policy = attributes.getString("policy");

        // 这样的话，如果在@EnableContentService注解的policy中使用core的话，应用程序会自动加载CoreContentService，否则会加载SimpleContentService。
        if ("core".equals(policy)) {
            return new String[] { CoreContentConfiguration.class.getName() };
        } else {
            return new String[] { SimpleContentConfiguration.class.getName() };
        }
    }
}

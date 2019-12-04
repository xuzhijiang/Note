package com.SourcePracticeDemo.annotation;

import com.SourcePracticeDemo.selector.ContentImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需求：定义一个Annotation，让使用了这个Annotaion的应用程序自动化地注入一些bean.
 *
 * 我们会使用@Import注解配合完成
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ContentImportSelector.class)// 我们在@EnableContentService注解添加属性policy，同时Import一个Selector。
public @interface EnableContentService {
    // 使用了EnableContentService注解的程序会自动注入policy对应的配置类
    String policy() default "simple";
}

// @Import(A.class)
// 这里的A要换成具体的SimpleContentConfiguration或者CoreContentConfiguration配置类,
// 或者换成实现ImportSelector接口的一个类,然后在这个接口类中,根据EnableContentService注解的
// policy,动态加载对应的配置类.

// SpringBoot也是用这个ImportSelector完成的
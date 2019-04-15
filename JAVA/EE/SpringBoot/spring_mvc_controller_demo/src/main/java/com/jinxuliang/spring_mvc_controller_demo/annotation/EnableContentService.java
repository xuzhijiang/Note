package com.jinxuliang.spring_mvc_controller_demo.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在分析开关的原理之前，我们来看一个需求：
 *
 * 定义一个Annotation，让使用了这个Annotaion的应用程序自动化地注入一些类的bean或者做一些底层的事情。
 * 我们会使用Spring提供的@Import注解配合一个配置类来完成。
 *
 * 下面定义一个注解EnableContentService，使用了这个注解的程序会自动注入ContentService这个bean。
 *
 * 然后在主应用程序的入口加上@EnableContentService注解。
 *
 * 这样的话，ContentService就被注入进来了。 SpringBoot也就是用这个完成的。
 * 只不过它用了更加高级点的ImportSelector。
 * 用了ImportSelector之后，我们可以在Annotation上添加一些属性，然后根据属性的不同加载不同的bean。
 *
 * 我们在@EnableContentService注解添加属性policy，同时Import一个Selector。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ContentImportSelector.class)
public @interface EnableContentService {
    String policy() default "simple";
}
package org.springboot.starter.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个starter内部定义了一个注解Log，使用这个注解修饰的方法，会打印出方法的耗时。
 *
 * starter支持exclude配置，在exclude中出现的方法不会进行计算。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {}

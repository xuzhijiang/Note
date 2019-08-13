package org.java.core.base.annotations.customJunitFramework;

import java.lang.annotation.*;

/**
 * 自定义Junit注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBefore {}

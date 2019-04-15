package com.jinxuliang.spring_mvc_controller_demo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
//@Import(ContentConfiguration.class), 这里的ContentConfiguration要换成具体的SimpleContentConfiguration或者CoreContentConfiguration,或者改成com.jinxuliang.spring_mvc_controller_demo.annotation.EnableContentService中的通过ContentImportSelector.class来动态选择
public @interface EnableContentServiceDemo {
}

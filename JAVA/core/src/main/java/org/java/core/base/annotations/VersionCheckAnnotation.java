package org.java.core.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //可标注元素为方法
@Retention(RetentionPolicy.SOURCE) //作用于源码时
public @interface VersionCheckAnnotation {
    // 一个major_version一个minor_version,
    // 若minor_version的值大于major_version的值，则提示错误

    // 有两个字段major_version代表主版本，minor_version代表次版本，
    // 默认值分别为1和0。
    int major_version() default 1;
    int minor_version() default 0;
}
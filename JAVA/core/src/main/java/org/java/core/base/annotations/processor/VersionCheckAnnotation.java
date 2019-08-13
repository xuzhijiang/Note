package org.java.core.base.annotations.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface VersionCheckAnnotation {
    int major_version() default 1;
    int minor_version() default 0;
}
package org.distributed.core.limit.redis.annotation;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SpringControllerLimit {

    /**
     * Error code
     */
    int errorCode() default 500;

    /**
     * Error message
     */
    String errorMsg() default "request limited";
}

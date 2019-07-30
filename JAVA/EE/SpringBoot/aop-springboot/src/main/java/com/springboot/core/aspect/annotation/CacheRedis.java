package com.springboot.core.aspect.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented // 注解是否将包含在 JavaDoc 中
public @interface CacheRedis {
    String key();
    int expireTime() default 800;
}

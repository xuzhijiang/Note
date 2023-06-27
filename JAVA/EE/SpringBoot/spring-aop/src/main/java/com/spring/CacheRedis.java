package com.spring;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRedis {
    String key();
    int expireTime() default 800;
}

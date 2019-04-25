package com.journaldev.spring.jdbc.routing;

import java.lang.annotation.*;

// @Routing可以在方法上使用，也可以在类或者接口上使用
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Routing {
    String value();
}

package org.java.core.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Java注解的保留策略(Retention)有3种，在RetentionPolicy中定义:
 * 1. SOURCE. 注解保留在源代码中，但是编译的时候会被编译器所丢弃。比如@Override, @SuppressWarnings
 * 2. CLASS. 这是默认的policy。注解会被保留在class文件中，但是在运行时期间就不会识别这个注解。
 * 3. RUNTIME. 注解会被保留在class文件中，同时运行时期间也会被识别。
 * 所以可以使用反射机制获取注解信息。比如@Deprecated
 *
 * 大部分情况下，我们都是使用RUNTIME这个Policy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyRuntimeAnnoClass {
    String name();
    int level() default 1;
}

@MyRuntimeAnnoClass(name = "simple", level = 2)
class SimpleObj {

}
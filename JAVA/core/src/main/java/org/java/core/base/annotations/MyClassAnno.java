package org.java.core.base.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CLASS和RUNTIME的唯一区别是RUNTIME在运行时期间注解是存在的，而CLASS则不存在。
 *
 * 我们通过asm来获取class文件里的annotation。
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface MyClassAnno {
    String name();
}

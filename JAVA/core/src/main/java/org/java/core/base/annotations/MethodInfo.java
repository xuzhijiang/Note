package org.java.core.base.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * 此注解MethodInfo有4种类型的元注解
 * @Target 表示MethodInfo这个注解适用的程序元素的种类是: method(方法).
 * 
 * 
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
	// author字段是String类型，默认值xzj
	String author() default "xzj";
	String date();
	// revision字段是int类型，默认值是1
	int revision() default 2;
	// comments字段是String类型，没有默认值,所以此字段在使用的时候不可缺失
	String comments();
}

package org.java.core.base.annotations.commonMethodTest;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfoAnnotation {
	String author() default "xzj";
	String date();
	int revision() default 2;

	/**
	 * comments字段是String类型，没有默认值,所以此字段在使用的时候不可缺失
	 */
	String comments();
}

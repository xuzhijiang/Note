package org.java.core.base.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * @Target – indicates the kinds of program element to which an annotation 
 * type is applicable(表示注释类型适用的程序元素的种类。). Some possible values are 
 * TYPE, METHOD, CONSTRUCTOR,FIELD etc. If Target meta-annotation is not present, 
 * then annotation can be used on any program element.如果目标元注解不存在，可以在任何程序上使用注解
 * <p><br>
 * @Inherited – indicates that an annotation type is automatically
 * inherited.表示自动继承注释类型。
 * If user queries the annotation type on a class 
 * declaration,如果用户在类声明上查询注解类型 and the class declaration has no annotation for 
 * this type并且类声明没有此类型注解, then the class’s superclass will automatically be 
 * queried for the annotation type(则类的超类将自动被查询以获取注解类型). 
 * This process will be repeated until an annotation for this type is found, 这个过程将会重复，直到此类型的注解被找到
 * or the top of the class hierarchy (Object) is reached.(达到类层次结构(对象)的顶部)
 * <p><br>
 * @Retention – indicates how long annotations with the 
 * annotated type are to be retained(表示带注解类型的注解被保留多长时间). It takes RetentionPolicy 
 * argument whose Possible values are SOURCE, CLASS and RUNTIME.( 它需要RetentionPolicy参数，其可能值为SOURCE，CLASS和RUNTIME)
 * <p><br>
 * Built-in annotations in Java(Java中内建的注解)
 * <p><br>
 * Java Provides three built-in annotations.(Java提供了3中内建注解)<br>
 * 1. @Override,我们用这个注解通知编译器我们正在覆盖父类的方法.所以当父类方法被删除或者被改变的
 * 时候，编译器将会展示错误消息
 * 2. @Deprecated, 我们想让编译器知道这个方法过时了，不建议使用了，我们应该提供为什么
 * 这个方法过时以及替代这个方法去使用的相关信息
 * 3. @SuppressWarnings,这个只是告诉编译器忽略某些警告，
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodInfo {
	String author() default "Pankaj";
	String date();
	int revision() default 1;
	String comments();
}

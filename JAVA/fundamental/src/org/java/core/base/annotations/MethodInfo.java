package org.java.core.base.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

/**
 * Annotations in java provide information about the code. 
 * Java annotations have no direct effect on the code (注解在Java中提供了代码的信息，Java注解对它们注解的代码没有直接的影响.)
 * they annotate. In java annotations tutorial, we will look into following:
 * <p><br>
 * annotations usage and how to parse annotations using reflection
 * <p><br>
 * Annotations are introduced in Java 1.5 and now it’s 
 * heavily used (大量使用)in Java EE frameworks like Hibernate, Jersey, Spring.
 * Java Annotation is metadata about the program embedded 
 * in the program itself. It can be parsed by the annotation
 * parsing tool or by compiler. We can also specify annotation 
 * availability to either compile time only or till runtime also.
 * (Java注解是嵌入在程序内部的关于程序的元素据,他可以通过注解解析工具或者编译器解析,我们可以指定注解在编译时可用或者直到运行时可用)
 * <p><br>
 * Java Custom Annotation
 * <p><br>
 * Creating custom annotation in java is similar to writing 
 * an interface(创建注解在Java中类似于写一个接口), except that it interface keyword is prefixed 
 * with @ symbol. We can declare methods in annotation.
 * <p><br>
 * Some important points about java annotations are:
 * <p>
 * Annotation methods can’t have parameters.(注解方法不能有参数)
 * Annotation methods return types are limited to primitives, String, Enums, Annotation or array of these.
 * (注解方法的返回类型被限制在原生类型，字符串，枚举，注解，数组)<p><br>
 * Java Annotation methods can have default values.(Java注解方法可以有默认值)
 * <p><br>
 * Annotations can have meta annotations attached to them. (注释可以附加元注释)
 * Meta annotations are used to provide information about the annotation.
 * (元注解适用于提供关于注解的信息)
 * <p><br>
 * Meta Annotations In Java(There are four types of meta annotations)
 * <p><br>
 * @Documented – indicates that elements using this annotation
 * should be documented by javadoc and similar tools(表示使用此批注的元素应由javadoc和类似工具记录。).
 * This type should be used to annotate the declarations of types whose 
 * annotations affect the use of annotated elements by their 
 * clients. If a type declaration is annotated with Documented, 
 * its annotations become part of the public API of the annotated elements.
 * <p><br>
 * @Target – indicates the kinds of program element to which an annotation 
 * type is applicable. Some possible values are TYPE, METHOD, CONSTRUCTOR,
 * FIELD etc. If Target meta-annotation is not present, then annotation
 * can be used on any program element.
 * <p><br>
 * @Inherited – indicates that an annotation type is automatically
 * inherited. If user queries the annotation type on a class 
 * declaration, and the class declaration has no annotation for 
 * this type, then the class’s superclass will automatically be 
 * queried for the annotation type. This process will be repeated until an annotation for this type is found, or the top of the class hierarchy (Object) is reached.
 * <p><br>
 * @Retention – indicates how long annotations with the 
 * annotated type are to be retained. It takes RetentionPolicy 
 * argument whose Possible values are SOURCE, CLASS and RUNTIME
 * <p><br>
 * Built-in annotations in Java
 * <p><br>
 * Java Provides three built-in annotations.
 * 
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

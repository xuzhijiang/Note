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
 * annotations usage and how to parse annotations using reflection(注解用法以及怎么用反射去解析注解)
 * <p><br>
 * Annotations are introduced in Java 1.5 and now it’s 
 * heavily used (大量使用)in Java EE frameworks like Hibernate, Jersey, Spring.
 * <br>
 * Java Annotation is metadata about the program embedded 
 * in the program itself. (Java注解是嵌入在程序内部的关于程序的元素据)
 * <br>
 * It can be parsed by the annotation
 * parsing tool or by compiler. (可以通过注解解析工具或者编译器解析)
 * <br>
 * We can also specify annotation availability to either compile time only or till runtime also.
 * (我们可以指定注解只在编译时可用或者直到运行时可用)
 * <p><br>
 * Annotations metadata can be available at runtime too and annotation 
 * parsers can use it to determine the process flow.
 * 注解元数据也可以在运行时变得可用，注解解析器可以用它去决定过程流。
 * <br>
 * For example, in Jersey webservice we add PATH annotation 
 * with URI string to a method and at runtime jersey parses it to determine 
 * the method to invoke for given URI pattern.
 * 例如，在Jersey webservice中，我们将带有URI字符串的PATH注释添加到方法中，
 * 并在运行时jersey解析它以确定为给定URI模式调用的方法。
 * <br>
 * Java Custom Annotation
 * <p><br>
 * Creating custom annotation in java is similar to writing 
 * an interface(创建注解在Java中类似于写一个接口), except that it interface keyword is prefixed 
 * with @ symbol. We can declare methods in annotation.
 * <p><br>
 * Some important points about java annotations are:
 * <p>
 * 1. Annotation methods can’t have parameters.(注解方法不能有参数)<br>
 * 2. Annotation methods return types are limited to primitives, 
 * String, Enums, Annotation or array of these.
 * (注解方法的返回类型被限制在原生类型，字符串，枚举，注解，数组)<p><br>
 * 3. Java Annotation methods can have default values.(Java注解方法可以有默认值)
 * <p><br>
 * 4. Annotations can have meta annotations attached to them. (注释可以附加元注释)
 * Meta annotations are used to provide information about the annotation.
 * (元注解适用于提供关于注解的信息)
 * <p><br>
 * Meta Annotations In Java(There are four types of meta annotations这里有4中类型的元注解)
 * <p><br>
 * 1. @Documented – indicates that elements using this annotation
 * should be documented by javadoc and similar tools
 * (表示使用此批注的元素应由javadoc和类似工具记录。)
 * This type should be used to annotate the declarations of types whose 
 * annotations affect the use of annotated elements by their 
 * clients. 
 * 此类型应用于注解类型的声明，其注解会影响其客户端对带注解元素的使用
 * If a type declaration is annotated with Documented, 如果使用Documented注解一个类型声明
 * its annotations become part of the public API of the annotated elements.
 * 则其注释将成为带注解元素的公共API的一部分。
 * <p><br>
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

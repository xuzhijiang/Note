Annotations(标注解释)

了解Java注释以及使用注释的好处。 Demo解释了java内置注释，如何创建自定义注释，
元注释以及我们如何使用Reflection API解析类的注释。

(注解在Java中提供了代码的信息，Java注解对它们注解的代码没有直接的影响.)

we will look into following:

1. (注解用法以及怎么用反射去解析注解)
2. Annotations在Java1.5被引入,now it’s heavily used (大量使用)in Java EE 
frameworks like Hibernate, Jersey, Spring.

For example, in Jersey webservice we add PATH annotation 
with URI string to a method and at runtime jersey parses 
it to determine the method to invoke for given URI pattern.

例如，在Jersey webservice中，我们将带有URI字符串的PATH注释添加到方法中，
并在运行时jersey解析它以确定为给定URI模式调用的方法。

3. Java注解是嵌入在程序内部的关于程序的metadata(元数据)
4. (可以通过注解解析工具或者编译器解析)
5. (我们可以指定注解只在编译时可用或者直到运行时可用)
6. Annotations metadata can be available at runtime too and annotation 
parsers can use it to determine the process flow.
注解元数据也可以在运行时变得可用，注解解析器可以用它去决定过程流。

Java Custom Annotation

Creating custom annotation in java is similar to writing 
an interface(创建注解在Java中类似于写一个接口), except that 
it interface keyword is prefixed 
with @ symbol. We can declare methods in annotation.
我们可以在注解中声明方法。

Some important points about java annotations are:

1. Annotation methods can’t have parameters.(注解方法不能有参数)
2. Annotation methods return types are limited to primitives, 
3. String, Enums, Annotation or array of these.
注解方法的返回类型被限制在原生类型，字符串，枚举，注解，数组

例如: WebSevelet中的这个String就是返回类型
public @interface WebServlet {
    
    /**
     * The name of the servlet
     */
    String name() default "";
}

4. Java Annotation methods can have default values. (Java注解方法可以有默认值)

5. Annotations can have meta annotations attached to them. (注解可以附加元注解)

6. Meta annotations are used to provide information about the annotation.
(元注解是用于提供关于注解的信息)

7. Meta Annotations In Java(There are four types of meta annotations这里有4中类型的元注解)
 
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


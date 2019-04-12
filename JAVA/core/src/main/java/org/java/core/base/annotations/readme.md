Demo解释了java内置注释，如何创建自定义注释，元注释以及我们如何使用Reflection API解析类的注释。

(注解在Java中提供了代码的信息，Java注解对它们注解的代码没有直接的影响.)

we will look into following:

>Annotations在Java1.5被引入,now it’s heavily used (大量使用)in Java EE
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
(元注解是用于提供关于注解的信息,也就是元注解是注解的注解)

## Meta Annotations In Java(Java中的元注解)

>There are four types of meta annotations这里有4中类型的元注解,
元注解是用来定义注解的注解.

### @Documented

表示使用此注解的元素,可以被javadoc此类工具文档化,也就是可以由javadoc和类似工具
来生成源码文档.如果使用Documented注解一个类型声明,则其注释将成为带注解元素的公共API的一部分。
此元注解应用于注解类型的声明。

### @Target

>@Target: 表示此注解修饰对象范围:Some possible values are TYPE(类，接口，枚举), FIELD，METHOD, 
PARAMETER，CONSTRUCTOR(构造器), LOCAL_VARIABLE，ANNOTATION_TYPE(用于注解类型,也就是它本身是元注解)，PACKAGE，TYPE_PARAMETER，TYPE_USE。
If Target meta-annotation is not present, then annotation can be used on any program element.
如果Target元注解不存在，可以在任何程序上使用注解

### @Inherited

>示自动继承注解类型。如果注解类型声明中存在Inherited元注释，
并且用户在类声明上查询注解类型，并且类声明没有此类型的注释，
则将自动查询类的超类以获取注释类型。这个过程将会重复，直到此类型的注解被找到，
或者达到类层次结构(对象)的顶部.This process will be repeated until an annotation for this type is found, 
or the top of the class hierarchy (Object) is reached.也就是如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。

#### Inherited的继承性

>@Inherited只是可控制对类名上注解是否可以被继承。不能控制方法上的注解是否可以被继承。
并且`类不从它所实现的接口继承annotation(接口的类以及接口的方法的注解都不可以被继承，无论
这个接口使用的注解是否使用了@Inherited元注解)`

<table>
    <thead>
        <tr>
            <th></th>
            <th>编写自定义注解时未写@Inherited的运行结果</th>
            <th>编写自定义注解时写了@Inherited的运行结果</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>子类的类上能否继承到父类的类上的注解？</td>
            <td>否</td>
            <td>能</td>
        </tr>
        <tr>
            <td>子类方法，实现了父类上的抽象方法，这个方法能否继承到注解？</td>
            <td>否</td>
            <td>否</td>
        </tr>
        <tr>
            <td>子类方法，继承了父类上的方法，这个方法能否继承到注解？</td>
            <td>能</td>
            <td>能</td>
        </tr>
        <tr>
            <td>子类方法，覆盖了父类上的方法，这个方法能否继承到注解？</td>
            <td>否</td>
            <td>否</td>
        </tr>    
    </tbody>
</table>

### @Retention 

>indicates how long annotations with the 
annotated type are to be retained(表示带注解类型的注解被保留多长时间). It takes RetentionPolicy 
argument whose Possible values are SOURCE, CLASS and RUNTIME.
(它需要RetentionPolicy参数，其可能值为SOURCE，CLASS和RUNTIME)

### Java Provides three built-in annotations

>Java提供了3中内建注解,也就是Java自带注解

1. @Override: 我们用这个注解通知编译器我们正在覆盖父类的方法.
所以当父类方法被删除或者被改变的时候，编译器将会展示错误消息
2. @Deprecated, 我们想让编译器知道这个方法过时了，不建议使用了，我们应该提供为什么
这个方法过时以及替代这个方法去使用的相关信息
3. @SuppressWarnings,这个只是告诉编译器忽略某些警告，

## 注解自身的继承性(注意区别与之前的@Inherited)

之前讨论的@Inherited的继承是指`类或接口`使用了注解A，注解A使用了@Inherited，那么`类或者接口`的`子类或者接口的实现类`是否能够继承注解A，但是这里讨论的继承指的是注解A，能够继承注解B。这里以Spring的注解举例.

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {
    String value() default "";
}

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseBody {
}

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    String value() default "";
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}

// 注解RestController相当于继承了注解@Controller和@ResponseBody的特征.
```
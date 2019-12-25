# 注解

Annotations在Java1.5被引入,在框架中被大量使用,注解和类、接口、枚举是同一级别的.

## 注解的特点

1. 注解的方法不能有参数
2. 注解方法的返回类型被限制在8种原生数据类型，String，枚举，Class(Object.class), 注解，以及这些类型的一维数组
3. 注解方法可以有默认值
4. 注解可以添加元注解和非元注解(相当于添加了非元注解的功能).
5. 元注解是用于提供关于注解的信息,也就是元注解是注解的注解,换句话说,原注解只能添加到注解上,不能添加到方法,类,等上.
6. value方法: 如果注解的方法(或叫属性)只有一个，且叫value，那么使用该注解时，可以不用指定属性名，因为默认就是给value赋值,(反正一旦注解可以不指定属性名赋值,那么就是赋值给value属性的)

# Java中的元注解

元注解是用来修饰注解的注解,有4中类型的元注解:@Documented, @Target, @Inherited, @Retention

## 1. @Documented

用于制作文档的,

表示使用此注解的元素,可以被javadoc类工具文档化,也就是可以由javadoc和类似工具
来生成源码文档.如果使用Documented注解一个类型声明,则其注释将成为带注解元素的公共API的一部分。
此元注解应用于注解类型的声明。

## 2. @Target

限定此注解修饰对象范围,常用的范围有:

- TYPE(类，接口，枚举)
- FIELD
- METHOD, 
- PARAMETER
- CONSTRUCTOR
- ANNOTATION_TYPE(用于注解类型,也就是它是元注解)

>如果@Target元注解不存在，可以在任何程序上使用注解

## 3. @Inherited

如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。

>如果注解类型I声明中存在Inherited元注释，并且用户在类A声明上查询注解类型I，并且类A声明没有使用注解I，则将自动查询类A的超类以获取注解I。这个过程将会重复，直到此类型的注解被找到，或者达到类层次结构(对象)的顶部

### Inherited的继承性

@Inherited只能控制对类名上注解是否可以被继承。不能控制方法上的注解是否可以被继承。

类不从它所实现的接口继承接口的annotation,也就是说接口的注解以及接口中的方法的注解都不可以被实现类继承，无论
这个接口使用的注解是否使用了@Inherited元注解

---
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

---

## 4. @Retention 

注解的保留策略,注解能被保留到哪个阶段. 值有3种,SOURCE, CLASS(默认值) and RUNTIME.

我们自定义的注解保留策略应该是RUNTIME，否则它的信息将无法在运行时获得.

>注解主要被反射读取,反射只能读取内存中的字节码信息,RetentionPolicy.CLASS指的是保留到字节码文件，它在磁盘内，而不是内存中。虚拟机将字节码文件加载进内存后注解会消失,要想被反射读取，保留策略只能用RUNTIME，即运行时仍可读取

注意:注解的读取并不只有反射一种途径。比如@Override，它由编译器读取，而编译器只是检查语法错误，此时程序尚未运行。

# Java自带注解

Java自带注解好多,这里举例:

1. @Override: 我们用这个注解通知编译器我们正在覆盖父类的方法.所以当父类方法被删除或者被改变的时候，编译器将会展示错误消息
2. @Deprecated, 我们想让编译器知道这个方法过时了，不建议使用了，我们应该提供为什么这个方法过时以及替代这个方法去使用的相关信息
3. @SuppressWarnings,这个只是告诉编译器忽略某些警告，

# 注解自身的继承性(注意区别与之前的@Inherited)

之前讨论的@Inherited的继承是指`类或接口`使用了注解A，注解A使用了@Inherited，那么`类或者接口`的`子类或者接口的实现类`是否能够继承注解A，但是这里讨论的继承指的是注解A，能够继承注解B的功能。这里以Spring的注解举例.

```java
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

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {
    String value() default "";
}

// 注解RestController相当于继承了注解@Controller和@ResponseBody的特征.
```

# 反射注解信息

要牢记，只要用到注解，必然有3步：定义注解，使用注解，反射读取注解。仅仅完成前两步，是没什么卵用的。


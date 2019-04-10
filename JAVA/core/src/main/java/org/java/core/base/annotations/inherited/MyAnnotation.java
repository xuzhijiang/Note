package org.java.core.base.annotations.inherited;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用@interface自定义注解时，自动继承了java.lang.annotation.Annotation接口，
 * 由编译程序自动完成其他细节。在定义注解时，不能继承其他的注解或接口。
 *
 * @interface用来声明一个注解，其中的每一个方法实际上是声明了一个配置参数。
 *
 * 方法的名称就是参数的名称，返回值类型就是参数的类型
 *
 * 可以通过default来声明参数的默认值
 *
 * 注解参数的可支持数据类型:
 * 所有基本数据类型（int,float,boolean,byte,double,char,long,short)
 * String类型
 * Class类型
 * enum类型
 * Annotation类型
 * 以上所有类型的数组
 *
 * 对局部变量的注解只能在源码级别上进行处理，class文件并不描述局部变量。
 * 因此，所有的局部变量注解在编译完一个类的时候就会被遗弃掉。同样的，对包的注解不能在源码级别之外存在。
 *
 * 一条没有@Target限制的注解可以应用于任何项上。
 *
 * @Inherited元注解只能应用于对类的注解(对接口没有作用)
 */
//@Inherited//测试是否可以被继承
@Retention(RetentionPolicy.RUNTIME)//可以通过反射读取注解
public @interface MyAnnotation {
    String value();
}

package org.java.core.advanced.jvm;

// RuntimeConstantPoolDemo.class这个二进制字节码Class文件由哪些东西组成呢?
// 主要是三部分组成: 类的基本信息 + 类的常量池(Constant pool) + 类的方法定义/类的字段定义等
// 可以通过javap -v xx.class查看

// 注意我这里讨论的是Class文件是由哪些部分组成,而不是讨论方法区的组成,方法区和Class文件是2个不同的东西.

// 字节码中的常量池(Constant pool)在Class被加载后,会存放到方法区中的"运行时常量池"中
// 字节码中的类方法定义/字段定义等类元信息会被加载到方法区中"运行时常量池"以外的内存中
public class RuntimeConstantPoolDemo {
    public static void main(String[] args) {
        System.out.println("Hello World!!");
    }
}

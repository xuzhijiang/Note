package org.java.core.advanced.jvm;

// RuntimeConstantPoolDemo.class这个二进制字节码由哪些东西组成呢?
// 主要是三部分组成: 类的基本信息 + 类的常量池 + 类方法定义/字段定义等(包含了虚拟机指令)
// 可以通过javap -v xx.class查看

// 注意这里说的是Class文件是由哪些部分组成组成,而不是方法区的组成,不要混了

// 字节码中的常量池在Class被加载后,会存放到方法区中的运行时常量池中
// 字节码中的类方法定义/字段定义等类元信息会被加载到方法区中运行时常量池以外的内存中
public class RuntimeConstantPoolDemo {
    public static void main(String[] args) {
        System.out.println("Hello World!!");
    }
}

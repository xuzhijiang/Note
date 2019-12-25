package org.java.core.advanced.jvm.codeblock;

/**
 * 构造代码块在每次创建对象时都会被调用,并且构造代码块的执行顺序优先于构造器
 */
public class CodeBlock02 {
    {
        System.out.println("33333");
    }

    public CodeBlock02() {
        System.out.println("222222");
    }

    {
        System.out.println("11111");
    }

    public static void main(String[] args) {
        new CodeBlock02();
        System.out.println("***********************");
        new CodeBlock02();
    }
}

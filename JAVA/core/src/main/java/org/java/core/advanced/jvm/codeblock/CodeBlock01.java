package org.java.core.advanced.jvm.codeblock;

/**
 * 普通代码块: 在方法或者语句中出现{}就称为普通代码块
 * 普通代码块和一般语句的执行顺序: 按他们在代码中的先后次序顺序执行
 */
public class CodeBlock01 {
    public static void main(String[] args) {
        {
            int x = 11;
            System.out.println("普通代码块内的变量x: " + x);
        }

        {
            int y = 12;
            System.out.println("普通代码块内的变量y: " + y);
        }

        int x = 13;
        System.out.println("普通代码块内的变量x: " + x);
    }
}

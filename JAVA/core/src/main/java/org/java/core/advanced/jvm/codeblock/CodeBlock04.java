package org.java.core.advanced.jvm.codeblock;

/**
 * 先有Father,才有Son
 */
public class CodeBlock04 {
    public static void main(String[] args) {
        System.out.println("777777777777777777");
        new Son();
        System.out.println("=======================");
        new Son();
        System.out.println("=======================");
        new Father();
    }
}

class Father {
    public Father() {
        System.out.println("11111111");
    }

    {
        System.out.println("2222222");
    }

    static {
        System.out.println("333333");
    }
}

class Son extends Father {
    public Son() {
        // super(); // 默认会调用这个super()
        System.out.println("4444");
    }

    {
        System.out.println("5555");
    }

    static {
        System.out.println("66666");
    }
}

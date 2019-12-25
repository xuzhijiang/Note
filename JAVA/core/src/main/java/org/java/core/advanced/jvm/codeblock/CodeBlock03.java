package org.java.core.advanced.jvm.codeblock;

public class CodeBlock03 {

    public CodeBlock03() {
        System.out.println("CodeBlock的构造方法666");
    }

    {
        System.out.println("CodeBlock的构造块4444");
    }

    static {
        System.out.println("CodeBlock的静态代码块555");
    }

    // 执行main方法之前要先加载CodeBlock.class,进而先触发CodeBlock的静态代码块
    public static void main(String[] args) {
        System.out.println("-----------------CodeBlock的main方法------77777");
        new CodeZY();
        System.out.println("--------------------------");
        new CodeZY();
        System.out.println("--------------------------");
        new CodeBlock03();
    }

}



class CodeZY {
    //执行顺序: 静态>构造块>构造方法.这个就是jvm的语法规定

    public CodeZY() {
        System.out.println("Code的构造方法1111");
    }

    // 非静态代码块:可以对类的属性（进行初始化
    // 一个类也可以有多个非静态的代码块，彼此之间顺序执行
    // 每创建一个对象，非静态代码块就加载一次，这个和静态代码块不一样
    {
        System.out.println("Code的构造块2222");
    }

    // 静态的东西 和 类的结构信息 会被加载到方法区.(类变量,类对象,类方法,是所有实例共用的)
    // 静态代码块:随着类的加载而加载，类的结构信息只会加载一次,所以静态的东西只会加载一次.
    static {
        System.out.println("Code的静态代码块3333");
    }
}
package org.java.core.advanced.jvm;

/**
 * static静态的，可以用来修饰变量，修饰方法，代码块，静态内部类.
 */
public class StaticTest {

    public static void main(String[] args) {
        Supperman.run();
        Supperman man = new Supperman();
        System.out.println("name: " + man.name);
    }

}

class Supperman {
    String name;
    int age;
    static String sex;

    // static关键字修饰的代码块分为静态代码块和非静态代码块,静态代码快的执行要早于非静态的

    static {//静态代码块:随着类的加载而加载，而且只被加载一次,
        // 静态的代码块里只能执行静态的结构（静态属性，静态方法）
        System.out.println("我是静态代码块");
    }

    // 非静态代码块:可以对类的属性（静态的或者非静态）进行初始化操作，
    // 同时也可以调用自身类中的方法（静态的或者非静态的）
    // 一个类也可以有多个非静态的代码块，彼此之间顺序执行
    // 每创建一个对象，非静态代码块就加载一次，这个和静态代码块不一样
    {
        System.out.println("我是非静态代码块");
        this.name = "name: 非静态代码块初始化的";
    }

    public static void run() {
        System.out.println("sex:" + sex);// String类型的默认值是null
    }
}
package org.java.core.base;

/**
 * Lambda表达式
 */
public class LambdaExpressDemo {

    public static void main(String[] args) {
        Foo foo = (x, y) -> {
            System.out.println("******hello lambda*******");
            return x + y;
        };

        System.out.println(foo.add(3, 5));
        System.out.println(foo.div(10, 5));
        System.out.println(Foo.multi(16, 14));
    }

    /**
     * 函数式接口:
     *      1. 一个接口中只有一个方法没有实现,这个interface就叫函数式接口.这样的接口,jdk会默认自动添加上@FunctionalInterface注解
     *      2. 标上@FunctionalInterface注解的接口,只允许有一个未实现的方法,如果有多个,会报错.
     *      3. 函数式接口可以允许有多个default的方法.
     *      4. 函数式接口能写成lambda表达式的原因: 有且仅有一个未实现的方法.(因为lambda不关心方法名)
     */
    @FunctionalInterface
    interface Foo {
        int add(int x, int y);

        // 请问? 接口中允不允许有方法的实现?
        // java8之前是不能,java8以及之后可以的.
        default int div(int x,int y) {
            System.out.println("hello interface default method");
            return x/y;
        }

        static int multi(int x,int y) {
            return x * y;
        }
    }

}

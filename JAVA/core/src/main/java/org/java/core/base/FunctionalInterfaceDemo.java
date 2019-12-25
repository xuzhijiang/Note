package org.java.core.base;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java.util.function
 * java内置核心4大函数式接口示例
 *
 * 链式编程 + 流式计算 + lambda表达式 现在一定要掌握.这些是你以后变为高级开发工程师躲不掉的,必须要走的路.
 */
public class FunctionalInterfaceDemo {
    public static void main(String[] args) {
        /**
         * Function<T, R> 函数型接口,有一个输入参数,有一个返回参数
         */
        /*Function<String, Integer> function = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return 1024;
            }
        };*/
        Function<String, Integer> function = s -> {return 1024;};
        System.out.println(function.apply("sss"));

        /**
         *
         */
        /*Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return false;
            }
        };*/
        Predicate<String> predicate = s -> {return s.isEmpty();};
        System.out.println(predicate.test("aaaa"));

        /**
         *
         */
        /*Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("********************: " + s);
            }
        };*/
        Consumer<String> consumer = s -> {System.out.println("********************: " + s);};
        consumer.accept("AAAA");

        /**
         *
         */
        /*Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1024;
            }
        };*/
        Supplier<Integer> supplier = () -> {return 1024;};
        System.out.println(supplier.get());
    }
}

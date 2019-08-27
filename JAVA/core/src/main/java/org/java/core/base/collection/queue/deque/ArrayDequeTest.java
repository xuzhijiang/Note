package org.java.core.base.collection.queue.deque;

import java.util.ArrayDeque;

public class ArrayDequeTest {


    public static void main(String[] args) {
        // 构造容量为4的数组，但是由于最小容量为8，所以构造的数组的容量是8
        ArrayDeque<String> arrayDeque = new ArrayDeque<>(4);
        arrayDeque.add("1");
        arrayDeque.add("2");
        arrayDeque.add("3");
        arrayDeque.addFirst("0.5");
        arrayDeque.add("4");

        // Deque接口同时还附带了Stack的功能。
        ArrayDeque<String> stack = new ArrayDeque<String>(4);
        stack.push("1");
        stack.push("2");
        stack.push("3");
        String pop = stack.pop();//3
        System.out.println("pop: " + pop);
    }

}

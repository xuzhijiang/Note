package org.java.core.base.collection.queue.deque;

import org.junit.Test;
import java.util.ArrayDeque;

public class ArrayDequeTest {

    @Test
    public void test01() {
        // 构造容量为4的数组，但是由于最小容量为8，所以构造的数组的容量是8
        ArrayDeque<String> arrayDeque = new ArrayDeque<>(4);
        arrayDeque.add("1"); //内部调用 addLast
        arrayDeque.add("2");
        arrayDeque.add("3");
        arrayDeque.addFirst("0.5");
        arrayDeque.add("4");

        for (String str : arrayDeque) {
            System.out.println("Str: " + str);
        }

        arrayDeque.remove(); // removeFirst

        arrayDeque.peek(); // peekFirst
    }

    /**
     * Deque接口同时还附带了Stack的功能
     */
    @Test
    public void test02() {
        ArrayDeque<String> stack = new ArrayDeque<>(4);
        stack.push("1");
        stack.push("2");
        stack.push("3");
        while (!stack.isEmpty()) {
            String pop = stack.pop();
            System.out.println("pop: " + pop);
        }
    }
}
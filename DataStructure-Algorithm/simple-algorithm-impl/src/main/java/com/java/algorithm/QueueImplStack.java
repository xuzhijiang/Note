package com.java.algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

// 用队列实现栈: 核心就是每次push的时候,把除了刚刚push进来的元素以外的所有元素都pop出来,然后重新push
public class QueueImplStack<T> {

    private Queue<T> queue;

    public QueueImplStack() {
        queue = new LinkedList<T>();
    }

    public void push(T element) {
        queue.offer(element);
        int size = queue.size();
        while (size > 1) { // 出了刚刚进入的元素不动,其他都要动.
            T e = queue.poll();
            queue.offer(e);
            size--;
        }
    }

    public T pop() {
        return queue.remove();
    }

    public T peek() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        QueueImplStack<Integer> stack = new QueueImplStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(5);
        stack.push(6);
        stack.push(7);
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }
}

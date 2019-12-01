package com.java.algorithm;

import java.util.Stack;

/**
 * 两个栈实现队列: output不为空就pop,如果output为空,把input的放到output中再pop
 */
public class TwoStackImplQueue<T> {

    private Stack<T> input;

    private Stack<T> output;

    public TwoStackImplQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }

    public void offer(T element) {
        input.add(element);
    }

    public T poll() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.add(input.pop());
            }
        }
        return output.pop();
    }

    public int size() {
        return input.size() + output.size();
    }

    public boolean isEmpty() {
        return output.isEmpty() && input.isEmpty();
    }

    public static void main(String[] args) {
        TwoStackImplQueue<Integer> queue = new TwoStackImplQueue<Integer>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        queue.offer(6);
        queue.offer(7);
        queue.offer(8);
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }

}

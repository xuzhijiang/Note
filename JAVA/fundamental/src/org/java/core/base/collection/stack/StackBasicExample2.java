package org.java.core.base.collection.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackBasicExample2 {
    public static void main(String a[]){
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        List<Integer> list = new ArrayList<>();
        list.addAll(stack);
        System.out.println("Non-Empty stack : "  + stack);
        System.out.println("Non-Empty List : "  + list);
    }
}

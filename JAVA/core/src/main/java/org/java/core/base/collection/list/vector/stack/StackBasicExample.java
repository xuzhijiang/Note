package org.java.core.base.collection.list.vector.stack;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackBasicExample {

	@Test
	public void testStackBasicFunction() {
		Stack<Integer> stack = new Stack<>();
		System.out.println("Empty stack : " + stack);
		System.out.println("Empty stack : " + stack.isEmpty());
		// Exception in thread "main" java.util.EmptyStackException
		// System.out.println("Empty stack : Pop Operation : " + stack.pop());
		stack.push(1001);
		stack.push(1002);
		stack.push(1003);
		stack.push(1004);
		System.out.println("Non-Empty stack : " + stack);
		System.out.println("Non-Empty stack: Pop Operation : " + stack.pop());
		System.out.println("Non-Empty stack : After Pop Operation : " + stack);
		System.out.println("Non-Empty stack : search() Operation : " + stack.search(1002));
		System.out.println("Non-Empty stack : " + stack.isEmpty());
	}

	@Test
	public void listToStack(){
		Stack<Integer> stack = new Stack<>();
		List<Integer> list = new ArrayList<>();
		list.add(1);list.add(2);list.add(3);
		System.out.println("Non-Empty stack addAll Operation : "  + stack.addAll(list));
		System.out.println("Non-Empty stack : "  + stack);
	}

	@Test
	public void arrayToStack(){
		Integer[] intArr = { 1001,1002,1003,1004};
		Stack<Integer> stack = new Stack<>();
		for(Integer i : intArr){
			stack.push(i);
		}
		System.out.println("Non-Empty stack : "  + stack);
	}

	@Test
	public void stackToList() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);stack.push(2);
		List<Integer> list = new ArrayList<>();
		list.addAll(stack);
		System.out.println(list);
	}
}

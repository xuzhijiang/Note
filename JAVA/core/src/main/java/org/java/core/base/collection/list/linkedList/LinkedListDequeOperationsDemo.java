package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.Deque;

// LinkedList实现了Deque,所以拥有Deque的特性,也就是可以当作队列来操作,
// 准确的是可以当作双端队列.
// 当然,因为Deque可以当作Stack,所以LinkedList也可以当作栈来使用>
public class LinkedListDequeOperationsDemo {
	public static void main(String[] args) {
		Deque<Integer> names = new LinkedList<Integer>();
		names.add(2);
		names.addFirst(1);
		names.addLast(3);
		names.addFirst(0);
		names.addLast(4);
		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
		
		names.removeFirst();
		names.removeLast();
		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
	}
}

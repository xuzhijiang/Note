package org.java.core.base.collection.linkedList;

import java.util.LinkedList;
import java.util.Deque;

/**
 * Java LinkedList Deque操作
 * 在这里，我们将探讨LinkedList对象如何作为Deque工作。 
 * 我们使用这些操作来实现队列或堆栈。
 * 我们将在后续帖子中讨论Stack或Queues如何深入工作。
 */
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

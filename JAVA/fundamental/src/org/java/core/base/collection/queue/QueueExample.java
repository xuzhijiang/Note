package org.java.core.base.collection.queue;

import java.util.*;

public class QueueExample {
	public static void main(String[] args) {

		Queue<String> queue = new LinkedList<>();
		queue.add("one");
		queue.add("two");
		queue.add("three");
		queue.add("four");
		System.out.println(queue);

		queue.remove("three");
		System.out.println(queue);
		System.out.println("Queue Size: " + queue.size());
		System.out.println("Queue Contains element 'two' or not? : " + queue.contains("two"));

		// To empty the queue
		queue.clear();
	}
}

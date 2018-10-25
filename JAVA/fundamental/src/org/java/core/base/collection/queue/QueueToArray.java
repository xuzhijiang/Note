package org.java.core.base.collection.queue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class QueueToArray {
	public static void main(String[] args) {
		Queue<String> queue = new LinkedList<>();
		queue.add("one");
		queue.add("two");
		queue.add("three");
		queue.add("four");
		queue.add("five");
		
		String strArray[] = queue.toArray(new String[queue.size()]);
		System.out.println(Arrays.toString(strArray));
	}
}

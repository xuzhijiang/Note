package org.java.core.base.collection.queue;

import java.util.*;

public class ArrayToQueue {
    public static void main(String[] args) {
		
	String nums[] = {"one","two","three","four","five"};
	Queue<String> queue = new LinkedList<>();
	Collections.addAll(queue, nums);
	System.out.println(queue);
   }
}

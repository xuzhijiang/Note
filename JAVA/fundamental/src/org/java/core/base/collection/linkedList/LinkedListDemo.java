package org.java.core.base.collection.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * Java LinkedList支持异构元素。 但是，建议不要使用没有泛型的集合。
 */
public class LinkedListDemo {
	public static void main(String[] args) {
		List names = new LinkedList();
		names.add("Rams");
		names.add("Posa");
		names.add("Chinni");
		names.add(2011);

		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
	}
}

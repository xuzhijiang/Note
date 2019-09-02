package org.java.core.base.collection.set.hashset;

import org.junit.Test;

import java.util.*;

public class HashSetExample {

	@Test
	public void testHashSet() {

		Set<String> fruits = new HashSet<>();
		fruits.add("Apple");
		fruits.add("Banana");
		
		List<String> list = new ArrayList<>();
		list.add("Apple"); list.add("Apple"); 
		list.add("Banana"); list.add("Mango");
		
		System.out.println("fruits set before addAll = "+fruits);
		System.out.println("list = "+list);
		fruits.addAll(list);
		System.out.println("fruits set after addAll = "+fruits);

		fruits.add("Orange");
		fruits.removeAll(list);
		System.out.println("fruits set after removeAll = "+fruits);
		
		fruits.clear();
		System.out.println("fruits set is empty = "+fruits.isEmpty());
	}

	@Test
	public void testLinkedHashSet() {
		Set<String> set = new LinkedHashSet<String>(5);
		set.add("java");
		set.add("golang");
		set.add("python");
		set.add("ruby");
		set.add("scala");

		for(String str : set) {
			System.out.println(str);
		}
	}
}

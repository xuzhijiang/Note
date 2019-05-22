package org.java.core.base.collection.set.hashset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HashSetExample {

	public static void main(String[] args) {

		Set<String> fruits = new HashSet<>();
		
		//add JdbcQuickStartExample
		fruits.add("Apple");
		fruits.add("Banana");
		
		//isEmpty JdbcQuickStartExample
		System.out.println("fruits set is empty = "+fruits.isEmpty());

		//contains JdbcQuickStartExample
		System.out.println("fruits contains Apple = "+fruits.contains("Apple"));
		System.out.println("fruits contains Mango = "+fruits.contains("Mango"));
		
		//remove JdbcQuickStartExample
		System.out.println("Apple removed from fruits set = "+fruits.remove("Apple"));
		System.out.println("Mango removed from fruits set = "+fruits.remove("Mango"));
		
		//size JdbcQuickStartExample
		System.out.println("fruits set size = "+fruits.size());
		
		//addAll JdbcQuickStartExample
		List<String> list = new ArrayList<>(); 
		list.add("Apple"); list.add("Apple"); 
		list.add("Banana"); list.add("Mango");
		
		System.out.println("fruits set before addAll = "+fruits);
		System.out.println("list = "+list);
		fruits.addAll(list);
		System.out.println("fruits set after addAll = "+fruits);

		//iterator JdbcQuickStartExample
		Iterator<String> iterator = fruits.iterator();
		while(iterator.hasNext()){
			System.out.println("Consuming fruit "+iterator.next());
		}
		
		//removeAll JdbcQuickStartExample
		fruits.add("Orange");
		System.out.println("fruits set before removeAll = "+fruits);
		System.out.println("list = "+list);
		fruits.removeAll(list);
		System.out.println("fruits set after removeAll = "+fruits);
		
		//clear JdbcQuickStartExample
		fruits.clear();
		System.out.println("fruits set is empty = "+fruits.isEmpty());

	}

}

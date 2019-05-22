package org.java.core.base.collection.list.arrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Below is a simple program for Arraylist JdbcQuickStartExample showing
 * commonly used methods.
 * @author PopovichXu
 */
public class ArrayListExample {

	public static void main(String args[]) {
		
		List<String> letters = new ArrayList<String>();
		
		//add JdbcQuickStartExample
		letters.add("A");
		letters.add("C");
		letters.add("D");
		
		letters.add(1, "B");
		System.out.println(letters);
		
		List<String> list = new ArrayList<String>();
		list.add("E");list.add("H");
		
		//appending list elements to letters
		letters.addAll(list);
		System.out.println(letters);
		
		//clear JdbcQuickStartExample to empty the list
		list.clear();
		
		list.add("F");list.add("G");
		
		//inserting list inside letters to get right sequence
		letters.addAll(5, list);
		System.out.println(letters);
		
		//contains JdbcQuickStartExample
		System.out.println("Letters list contains E ? "+letters.contains("E"));
		System.out.println("Letters list contains Z ? "+letters.contains("Z"));
		
		//ensureCapacity JdbcQuickStartExample, it's ArrayListSource method, so object should be defined like below.
		ArrayList<String> tempList = new ArrayList<>();
		tempList.ensureCapacity(1000);
		
		//get JdbcQuickStartExample
		String e = letters.get(4);
		System.out.println("Letter at 5th place: "+e);
		
		//tempList is empty?
		System.out.println("tempList is empty ? "+tempList.isEmpty());
		
		//indexOf JdbcQuickStartExample
		System.out.println("First index of D = "+letters.indexOf("D"));
		System.out.println("Last index of D = "+letters.lastIndexOf("D"));
		
		//remove examples
		System.out.println(letters);
		String removed = letters.remove(3);
		System.out.println("After removing '"+removed+"' letters contains "+letters);
		
		//remove first occurrence of H
		boolean isRemoved = letters.remove("H");
		System.out.println("H removed? "+isRemoved+". Letters contains "+letters);
		System.out.println("list contains "+list);
		
		//remove all matching elements between letters and list
		letters.removeAll(list);
		System.out.println(letters);
		
		//retainAll JdbcQuickStartExample БЃСєЫљга
		list.clear();list.add("A");list.add("B");list.add("C");
		letters.retainAll(list);
		System.out.println("letters elements after retainAll operation: "+letters);
		
		//size JdbcQuickStartExample
		System.out.println("letters ArrayListSource size = "+letters.size());
		
		//set JdbcQuickStartExample
		letters.set(2, "D");
		System.out.println(letters);
		
		//toArray JdbcQuickStartExample
		String[] strArray = new String[letters.size()];
		strArray = letters.toArray(strArray);
		System.out.println(Arrays.toString(strArray));
	}
}

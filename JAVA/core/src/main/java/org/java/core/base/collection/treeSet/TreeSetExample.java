package org.java.core.base.collection.treeSet;

import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetExample {
	public static void main(String[] args){
		//“‘◊‘»ªÀ≥–Ú≈≈–Ú
		// Create a sorted set of Integers
		SortedSet<Integer> setWithNaturalOrdering = new TreeSet<>();
		setWithNaturalOrdering.add(5);
		setWithNaturalOrdering.add(9);
		setWithNaturalOrdering.add(4);
		setWithNaturalOrdering.add(2);
		setWithNaturalOrdering.forEach(System.out::println);

	}
}

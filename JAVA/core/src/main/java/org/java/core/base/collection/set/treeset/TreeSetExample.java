package org.java.core.base.collection.set.treeset;

import java.util.SortedSet;
import java.util.TreeSet;

public class TreeSetExample {
	public static void main(String[] args){
		//����Ȼ˳������
		// Create a sorted set of Integers
		SortedSet<Integer> setWithNaturalOrdering = new TreeSet<>();
		setWithNaturalOrdering.add(5);
		setWithNaturalOrdering.add(9);
		setWithNaturalOrdering.add(4);
		setWithNaturalOrdering.add(2);
		setWithNaturalOrdering.forEach(System.out::println);

	}
}

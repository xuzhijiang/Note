package org.java.core.base.collection.set.hashset;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConstructorExample {

	public static void main(String[] args) {
	
		Set<String> set = new HashSet<>();
		
		//initial capacity should be power of 2
		set = new HashSet<>(32); 
		
		//setting backing HashMap initial capacity and load factor
		set = new HashSet<>(32, 0.80f);
		
		//creating HashSet from another Collection
		Set<String> set1 = new HashSet<>(set);
		Set<String> set2 = new HashSet<>(new ArrayList<>());

	}
}

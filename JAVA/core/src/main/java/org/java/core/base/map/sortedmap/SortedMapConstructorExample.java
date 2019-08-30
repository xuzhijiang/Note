package org.java.core.base.map.sortedmap;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

// TreeMap是SortedMap的一种典型实现
public class SortedMapConstructorExample {
	
	public static void main(String[] args) {

		SortedMap<String, PersonalDetails> personMap = new TreeMap<>();
		personMap.put("Dan Brown", new PersonalDetails("WorkerThread", LocalDate.of(1964, 6, 22), "New Hampshire"));
		personMap.put("Ayn Rand", new PersonalDetails("WorkerThread", LocalDate.of(1905, 2, 2), "Saint Petersburg"));
		personMap.put("Devdutt Pattanaik", new PersonalDetails("Mythologist", LocalDate.of(1970, 12, 11), "Mumbai"));
		
		personMap.keySet().forEach(key -> {
			System.out.println(key + " -> " + personMap.get(key));
		});

		// It will print the map according to natural order of its keys. 
		// In this case, key type is String, which implements Comparable interface.
		
		// 如果我们在构造函数参数中提供Comparator实现，则输出将具有不同的排序
		// Let’s say we want to sort the keys as per key lengths in their descending order:
		SortedMap<String, PersonalDetails> personMap1 = new TreeMap<>((s1, s2) -> s2.length() - s1.length());
		personMap1.put("Dan Brown", new PersonalDetails("WorkerThread", LocalDate.of(1964, 6, 22), "New Hampshire"));
		personMap1.put("Ayn Rand", new PersonalDetails("WorkerThread", LocalDate.of(1905, 2, 2), "Saint Petersburg"));
		personMap1.put("Devdutt Pattanaik", new PersonalDetails("Mythologist", LocalDate.of(1970, 12, 11), "Mumbai"));
		
		personMap1.keySet().forEach(key -> {
			System.out.println(key + " -> " + personMap1.get(key));
		});
		
		// we'll create a sorted map by passing another map object or a different sorted map.
		System.out.println("\npassing another map object: ");
		Map<String, PersonalDetails> map = new HashMap<>();
		map.put("Dan Brown", 
		  new PersonalDetails("WorkerThread", LocalDate.of(1964, 6, 22), "New Hampshire"));
		map.put("Ayn Rand",
		  new PersonalDetails("WorkerThread", LocalDate.of(1905, 2, 2), "Saint Petersburg"));
		map.put("Devdutt Pattanaik",
		  new PersonalDetails("Mythologist", LocalDate.of(1970, 12, 11), "Mumbai"));
		
		SortedMap<String, PersonalDetails> sortedMap = new TreeMap<>(map);
		sortedMap.keySet().forEach(key -> {
		    System.out.println(key + " -> " + sortedMap.get(key));
		});
		
		System.out.println("\nSorted Map constructed using another sorted map:");
		SortedMap<String, PersonalDetails> copiedMap = new TreeMap<>(sortedMap);
		copiedMap.keySet().forEach(key -> {
		    System.out.println(key + " -> " + copiedMap.get(key));
		});

	}
	
}
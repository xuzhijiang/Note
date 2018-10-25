package org.java.core.base.collection.sortedMap;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

// Let’s explore all of these methods one by one. We’ll create a sorted 
//map by passing a comparator and the comparator() method will return the same comparator.
// 我们通过传递comparator来创建一个Sorted map，comparator()将返回相同的比较器
public class SortedMapImplementationExample {

	public static void main(String[] args) {

		SortedMap sortedMap0 = new TreeMap(Comparator.reverseOrder());
		Comparator comparator = sortedMap0.comparator();

		SortedMap<String, PersonalDetails> sortedMap = new TreeMap<>(Comparator.reverseOrder());
		sortedMap.put("Dan Brown", new PersonalDetails("Writer", LocalDate.of(1964, 6, 22), "New Hampshire"));
		sortedMap.put("Ayn Rand", new PersonalDetails("Writer", LocalDate.of(1905, 2, 2), "Saint Petersburg"));
		sortedMap.put("Devdutt Pattanaik", new PersonalDetails("Mythologist", LocalDate.of(1970, 12, 11), "Mumbai"));
		Set<Map.Entry<String, PersonalDetails>> entrySet = sortedMap.entrySet();
		entrySet.forEach(entry -> {
			System.out.println(entry.getKey() + "->" + entry.getValue());
		});

		// keySet()方法返回Set of keys, 然而values()将返回the collection of values().
		sortedMap.keySet().forEach(System.out::println);
		Collection<PersonalDetails> values = sortedMap.values();
		values.forEach(System.out::println);
		
		// Also, there are methods available to get first key, 
		// last key and portion of the map which contains keys less 
		// than or greater than equal to a particular key.
		System.out.println("Smallest and largest keys of the map:");
		System.out.println(sortedMap.firstKey());
		System.out.println(sortedMap.lastKey());

		System.out.println("Head map containing keys whose values are less than D.");
		SortedMap headMap = sortedMap.headMap("D");
		headMap.keySet().forEach(System.out::println);

		System.out.println("Tail map containing keys whose values are greater than or equal to D:");
		SortedMap tailMap = sortedMap.tailMap("D");
		tailMap.keySet().forEach(System.out::println);

	}
	
}

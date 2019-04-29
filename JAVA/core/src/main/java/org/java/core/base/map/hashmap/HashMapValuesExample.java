package org.java.core.base.map.hashmap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Java HashMap values方法返回Map中值的Collection视图。 
// 此集合由HashMap支持，因此HashMap中的任何更改都将反映在值集合中，
public class HashMapValuesExample {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", null);
		map.put("4", null);
		map.put(null, "d");

		Collection<String> values = map.values();
		System.out.println("map values = " + values);

		map.remove(null);
		System.out.println("map values after removing null key = " + values);

		map.put("5", "5");
		System.out.println("map values after put = " + values);

		System.out.println(map);
		values.remove("a"); // changing values collection
		System.out.println(map); // updates in map too

	}

}

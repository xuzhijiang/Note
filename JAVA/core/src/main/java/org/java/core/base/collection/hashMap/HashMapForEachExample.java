package org.java.core.base.collection.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

//HashMap forEach方法是在Java 8中引入的。它是一种非常有用的方法，
//可以为地图中的每个条目执行给定的操作，直到处理完所有条目或操作抛出异常为止。
public class HashMapForEachExample {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", null);
		map.put(null, "100");

		BiConsumer<String, String> action = new MyBiConsumer();
		map.forEach(action);
		
		//lambda expression example
		System.out.println("\nHashMap forEach lambda example\n");
		map.forEach((k,v) -> {System.out.println("Key = "+k+", Value = "+v);});
	}

}

class MyBiConsumer implements BiConsumer<String, String> {

	@Override
	public void accept(String t, String u) {
		System.out.println("Key = " + t);
		System.out.println("Processing on value = " + u);
	}

}

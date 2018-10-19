package org.java.core.base.collection.hashMap;

import java.util.HashMap;
import java.util.Map;

public class HashMapConstructorsExample {
	public static void main(String[] args) {

		Map<String, String> map1 = new HashMap<>();
		
		Map<String, String> map2 = new HashMap<>(2^5);
		
		Map<String, String> map3 = new HashMap<>(32,0.80f);
		
		Map<String,String> map4 = new HashMap<>(map1);

	}
}

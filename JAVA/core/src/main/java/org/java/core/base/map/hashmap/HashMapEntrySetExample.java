package org.java.core.base.map.hashmap;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// Java HashMap entrySet method returns the Set view of mappings.
// This entrySet is backed by HashMap, so any changes 
// in map reflects in entry set and vice versa. 
public class HashMapEntrySetExample {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", null);
		map.put(null, "100");

		Set<Entry<String,String>> entrySet = map.entrySet();
		Iterator<Entry<String, String>> iterator = entrySet.iterator();
		Entry<String, String> next = null;
		
		System.out.println("map before processing = "+map);
		System.out.println("entrySet before processing = "+entrySet);
		while(iterator.hasNext()){
			next = iterator.next();
			System.out.println("Processing on: "+next.getValue());
			if(next.getKey() == null) iterator.remove();
		}
		
		System.out.println("map after processing = "+map);
		System.out.println("entrySet after processing = "+entrySet);
		
		Entry<String, String> simpleEntry = new AbstractMap.SimpleEntry<String, String>("1","1");
		entrySet.remove(simpleEntry);
		System.out.println("map after removing Entry = "+map);
		System.out.println("entrySet after removing Entry = "+entrySet);
	}

}

package org.java.core.base.collection.treeMap;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

// natural sorting 
public class JavaTreeMapExample {

	public static void main(String[] args) {
		
		// 请注意，当我们在创建TreeMap时不提供Comparator时，它使用Integer 
		// compareTo方法来排序键。 这就是为什么键按顺序递增，即使我们按任何顺序插入它。
		Map<Integer,String> map = new TreeMap<>();
		
		map.put(10, "10");
		map.put(1, "1");
		map.put(5, "5");
		
		System.out.println(map);
		
		map = new TreeMap<>(new Comparator<Integer>() {

			@Override
			public int compare(Integer x, Integer y) {
				return (x > y) ? -1 : ((x == y) ? 0 : 1);
			}
			
		});
		map.put(10, "10");
		map.put(1, "1");
		map.put(5, "5");
		System.out.println(map);

	}

}

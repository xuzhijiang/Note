package org.java.core.base.map.treemap;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class JavaTreeMapExample {

	public static void main(String[] args) {
		Map<Integer,String> map = new TreeMap<>();
		// 没有传入比较器,那么就按照key的自然顺序进行排序(依据Integer的compareTo方法)
		map.put(10, "10");
		map.put(1, "1");
		map.put(5, "5");
		System.out.println(map);
		// 传入比较器,那么key会按照传入的比较器的策略进行排序(策略模式)
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

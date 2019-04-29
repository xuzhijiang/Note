package org.java.core.base.map.treemap;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

// �ó�����ʾ��NavigableMap�������÷���
public class JavaTreeMapNavigationExamples {

	public static void main(String[] args) {
		
		//we have to define object as TreeMap to use NavigableMap functions
		TreeMap<Integer,String> map = new TreeMap<>();
		for(int i=0;i<10;i++) {
			map.put(i, i+"");
		}
		
		System.out.println(map);
		
		//find id closest to 5, lower and higher
		Entry<Integer,String> entry = map.lowerEntry(5);
		System.out.println("Closest Lower key than 5 is "+entry);
		entry = map.higherEntry(5);
		System.out.println("Closest Higher key than 5 is "+entry);
		
		System.out.println("Closest Lower key than 4 is "+map.lowerKey(4));
		
		entry = map.floorEntry(5);
		System.out.println("Closest floor entry than 5 is "+entry);
		
		entry = map.ceilingEntry(4);
		System.out.println("Closest ceiling key than 4 is "+entry);
		
		entry = map.firstEntry();
		System.out.println("First Entry is "+entry);

		entry = map.lastEntry();
		System.out.println("Last Entry is "+entry);
		
		Map<Integer, String> reversedMap = map.descendingMap();
		System.out.println("Reversed Map: "+reversedMap);
		
		//poll and remove first, last entries
		entry = map.pollFirstEntry();
		System.out.println("First Entry is "+entry);
		entry = map.pollLastEntry();
		System.out.println("Last Entry is "+entry);
		System.out.println("Updated Map: "+map);
		
		//submap example
		Map<Integer, String> subMap = map.subMap(2, true, 6, true);
		System.out.println("Submap: "+subMap);
		
		subMap = map.headMap(5, true);
		System.out.println("HeadMap: "+subMap);

		subMap = map.tailMap(5, true);
		System.out.println("TailMap: "+subMap);
	}

}

package org.java.core.base.collection.iterator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HowIteratorWorksExample {
	
	public static void main(String[] args) {
		
		List<String> names = new LinkedList<>();
		names.add("E-1");
		names.add("E-2");
		names.add("E-3");
		names.add("E-n");
		
		Iterator<String> namesIterator = names.iterator();

		while(namesIterator.hasNext()) {
			System.out.println(namesIterator.next());
		}
	}
	
}

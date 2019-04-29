package org.java.core.base.iterator.listItrator;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorWorksDemo {
	public static void main(String[]  args){

		List<String> names = new LinkedList<>();
		names.add("E-1");
		names.add("E-2");
		names.add("E-3");
		names.add("E-n");

		ListIterator<String> namesIterator = names.listIterator();
		
		System.out.println(namesIterator.hasPrevious());
		while(namesIterator.hasPrevious()){
			System.out.println(namesIterator.previous());
		}
	}
}

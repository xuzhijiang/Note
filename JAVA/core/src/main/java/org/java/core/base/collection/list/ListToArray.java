package org.java.core.base.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListToArray {
	public static void main(String[] args) {
		List<String> letters = new ArrayList<String>();
		
		//add JdbcQuickStartExample
		letters.add("A");
		letters.add("B");
		letters.add("C");
		
		//convert list to Array
		String[] strArray = new String[letters.size()];
		letters.toArray(strArray);
		System.out.print(Arrays.toString(strArray));
	}
}

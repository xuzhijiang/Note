package org.java.core.base.collection.list.arrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ArrayListListIteratorExample {
	
	public static void main(String[] args) {

		List<Integer> ints = new ArrayList<>();
		for (int i = 0; i < 10; i++) ints.add(i);
		
		ListIterator<Integer> lit = ints.listIterator(ints.size());
		
		while(lit.hasPrevious()){
			int x = lit.previous();
			System.out.print(x + ", ");
			if(x==5){
				lit.remove();
				lit.add(20);
			}
		}
		System.out.println("\n"+ints);
	}
}

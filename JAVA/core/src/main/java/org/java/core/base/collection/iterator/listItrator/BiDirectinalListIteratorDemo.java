package org.java.core.base.collection.iterator.listItrator;

import java.util.*;

public class BiDirectinalListIteratorDemo 
{
	public static void main(String[] args) 
	{
		List<String>names = new LinkedList<>();
		names.add("Rams");
		names.add("Posa");
		names.add("Chinni");
		
		// Getting ListIterator
		ListIterator<String> listIterator = names.listIterator();
		
		// Traversing elements
		System.out.println("Forward Direction Iteration:");
		while(listIterator.hasNext()){
			System.out.println(listIterator.next());			
		}	
		
		// Traversing elements
		System.out.println("Backward Direction Iteration:");
		while(listIterator.hasPrevious()){
			System.out.println(listIterator.previous());			
		}
	}
}

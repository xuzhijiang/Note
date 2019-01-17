package org.java.core.base.collection.hashSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 有时我们必须将HashSet转换为数组，反之亦然.(vice-versa)
 *
 */
public class HashSetToArrayExample {

	public static void main(String[] args) {
		Set<Integer> ints = new HashSet<>();
		for(int i=0; i<10; i++){
			ints.add(i);
		}
		System.out.println("ints set = "+ints);
		
		// set to array example
		Integer[] intArray = new Integer[ints.size()];
		intArray = ints.toArray(intArray);
		System.out.println("intArray = "+Arrays.toString(intArray));
		ints.remove(0);ints.remove(1);
		System.out.println("intArray = "+Arrays.toString(intArray));
		
		
		//array to set example
		ints = new HashSet<>(Arrays.asList(intArray));
		System.out.println("ints from array = "+ints);
		ints.remove(0);ints.remove(1);
		System.out.println("ints from array after remove = "+ints);
		System.out.println("intArray = "+Arrays.toString(intArray));


	}

}

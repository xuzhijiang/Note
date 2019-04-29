package org.java.core.base.collection.set.hashset;

/**
 * There is not much difference between Set and List, 
 * but sometimes we have to convert from Set to List or 
 * List to Set. Below is a simple example showing correct 
 * way to convert Set to List and then List to Set in java.
 * 
 * Set��List֮��û��̫�����𣬵���ʱ���Ǳ����Setת��ΪList��Listת��ΪSet
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashSetToListExample {

	public static void main(String[] args) {
		Set<String> vowels = new HashSet<>();
		vowels.add("a"); vowels.add("e"); vowels.add("i");
		
		//set to list example
		List<String> vowelsList = new ArrayList<>(vowels);
		System.out.println("vowels set = "+vowels);
		System.out.println("vowelsList = "+vowelsList);
		
		vowels.add("o");
		vowelsList.add("a");vowelsList.add("u");
		System.out.println("vowels set = "+vowels);
		System.out.println("vowelsList = "+vowelsList);
		
		//list to set example
		vowels = new HashSet<>(vowelsList);
		System.out.println("vowels set = "+vowels);
	}

}

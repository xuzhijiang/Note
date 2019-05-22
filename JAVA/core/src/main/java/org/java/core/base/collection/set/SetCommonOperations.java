package org.java.core.base.collection.set;

import java.util.*;

// ��Java Set��ִ�е����������add��addAll��clear��size�ȡ�
public class SetCommonOperations 
{
   public static void main(String args[]) 
   {
	Set<String> vowels= new HashSet<>();
		
	//add JdbcQuickStartExample
	vowels.add("A");
	vowels.add("E");
	vowels.add("I");

	//We cannot insert elements based on index to a Set
	System.out.println(vowels);
		
	Set<String> set = new HashSet<>();
	set.add("O");
	set.add("U");
	
	//appending set elements to letters
	vowels.addAll(set);
	System.out.println(vowels);
	
	//clear JdbcQuickStartExample to empty the set
	set.clear();
		
	//size JdbcQuickStartExample
	System.out.println("letters set size = " + vowels.size());
		
	vowels.clear();
	vowels.add("E"); vowels.add("E");vowels.add("I"); vowels.add("O");
	System.out.println("Given set contains E element or not? = " + vowels.contains("E"));
		
   }
}

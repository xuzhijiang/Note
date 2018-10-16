package org.java.core.base.collection.set;

import java.util.*;

/**
 * 在这种方法中，我们不使用中间List来从Array创建Set。
 *  首先创建一个空的HashSet，然后使用Collections.addAll（）将数组元素复制到给定的Set中，
 *
 */
public class ArrayToSet2 {
   public static void main(String[] args) {
		
	String[] vowels = {"a","e","i","o","u"};
		
	Set<String> vowelsSet = new HashSet<>();
	
	Collections.addAll(vowelsSet, vowels); 
	System.out.println(vowelsSet);

	/** 
	 * Unlike List, Set is NOt backed by array, 
	 * so we can do structural modification without any issues.
	 */
	vowelsSet.remove("e");
	System.out.println(vowelsSet);
	vowelsSet.clear();
	System.out.println(vowelsSet);
   }
}

package org.java.core.base.collection.set;

import java.util.*;

// 使用Set.toArray（）方法将一组字符串转换为字符串数组
public class SetToArray {
   public static void main(String[] args) {
	Set<String> vowelsSet = new HashSet<>();

	// add example
	vowelsSet.add("a");
	vowelsSet.add("e");
	vowelsSet.add("i");
	vowelsSet.add("o");
	vowelsSet.add("u");
		
	//convert Set to Array
	String strArray[] = vowelsSet.toArray(new String[vowelsSet.size()]);
	System.out.println(Arrays.toString(strArray)); 
   }
}

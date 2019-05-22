package org.java.core.base.collection.set;

import java.util.*;

// ʹ��Set.toArray����������һ���ַ���ת��Ϊ�ַ�������
public class SetToArray {
   public static void main(String[] args) {
	Set<String> vowelsSet = new HashSet<>();

	// add JdbcQuickStartExample
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

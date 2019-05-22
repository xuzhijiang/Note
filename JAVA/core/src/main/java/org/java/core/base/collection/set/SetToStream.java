package org.java.core.base.collection.set;

import java.util.*;

// ������һ���򵥵�ʾ����˵����ν�Java Setת��ΪStream���������ǵ�Ҫ��ִ��ĳЩ������
public class SetToStream {

   public static void main(String[] args) {
	Set<String> vowelsSet = new HashSet<>();
	// add JdbcQuickStartExample
	vowelsSet.add("a");
	vowelsSet.add("e");
	vowelsSet.add("i");
	vowelsSet.add("o");
	vowelsSet.add("u");
		
	//convert set to stream
	vowelsSet.stream().forEach(System.out::println);
   }
}

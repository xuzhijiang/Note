package org.java.core.base.collection.linkedList;

import java.util.LinkedList;
import java.util.List;

// 在这里，我们将探讨如何将LinkedList对象转换为Java SE 8 Stream概念。
// Stream是Java8引入的概念
public class LinkedListToStreamDemo 
{
  public static void main(String[] args) 
  {		
	List<Integer> numbersList = new LinkedList<>();
	numbersList.add(1);
	numbersList.add(2);
	numbersList.add(3);
	numbersList.add(4);
	numbersList.add(5);
		
	//convert List to stream
	numbersList.stream().forEach(System.out::println);
  }
}

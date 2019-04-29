package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.List;

// ��������ǽ�̽����ν�LinkedList����ת��ΪJava SE 8 Stream���
// Stream��Java8����ĸ���
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

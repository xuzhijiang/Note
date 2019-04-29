package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * ���ǽ�̽����ν�Java Arrayת��ΪLinkedList����
 */
public class JavaArrayToLinkedListDemo 
{
	public static void main(String[] args) 
	{
		Integer[] numbers = {1,2,3,4,5};
		List<Integer> numbersList = new LinkedList<>();
		for(Integer s : numbers){
			numbersList.add(s);
		}
		System.out.println(numbersList);
	}
}

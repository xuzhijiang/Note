package org.java.core.base.collection.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * 我们将探讨如何将Java Array转换为LinkedList对象
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

package org.java.core.base.collection.list.arrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayListExample {

	public static void main(String args[]) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		System.out.printf("Before add:arrayList.size() = %d\n",arrayList.size());

		arrayList.add(1);
		arrayList.add(3);
		arrayList.add(5);
		arrayList.add(7);
		arrayList.add(9);
		System.out.printf("After add:arrayList.size() = %d\n",arrayList.size());

		System.out.println("Printing elements of arrayList");


		// toArray用法
		// 第一种方式(最常用)
		Integer[] integer = arrayList.toArray(new Integer[0]);

		// 第二种方式(容易理解)
		Integer[] integer1 = new Integer[arrayList.size()];
		arrayList.toArray(integer1);

		// 抛出异常，java不支持向下转型
		//Integer[] integer2 = new Integer[arrayList.size()];
		//integer2 = arrayList.toArray();
		System.out.println();

		// 在指定位置添加元素
		arrayList.add(2,2);
		// 删除指定位置上的元素
		arrayList.remove(2);
		// 删除指定元素
		arrayList.remove((Object)3);
		// 判断arrayList是否包含5
		System.out.println("ArrayList contains 5 is: " + arrayList.contains(5));

		// 清空ArrayList
		arrayList.clear();
		// 判断ArrayList是否为空
		System.out.println("ArrayList is empty: " + arrayList.isEmpty());

	}
}

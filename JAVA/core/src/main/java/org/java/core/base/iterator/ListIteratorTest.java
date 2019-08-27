package org.java.core.base.iterator;

import org.junit.Test;

import java.util.*;

/**
 * 1. ListIterator它从Java 1.2开始提供
 * 2. 与Iterator(也是Java 1.2中引入)不同，它支持前向和后向迭代。它是一个双向迭代器。
 * 3. ListIterator有以下一些限制: 与Iterator不同，它不适用于整个Collection API,只能用于用于所有List实现的类.
 *
 * public interface Iterator<E> {}
 * public interface ListIterator<E> extends Iterator<E> {}
 *
 * void add（E e）：将指定的元素插入列表中。
 * boolean hasNext（）：如果此列表迭代器在向前遍历列表时具有更多元素，则返回true。
 * boolean hasPrevious（）：如果此列表迭代器在反向遍历列表时具有更多元素，则返回true。
 * E next（）：返回列表中的下一个元素并前进光标位置。
 * int nextIndex（）：返回后续调用next（）返回的元素的索引。
 * E previous（）：返回列表中的上一个元素并向后移动光标位置。
 * int previousIndex（）：返回后续调用previous（）返回的元素的索引。
 * void remove（）：从列表中删除next（）或previous（）返回的最后一个元素。
 * void set（E e）：用指定的元素替换next（）或previous（）返回的最后一个元素。
 */
public class ListIteratorTest
{
	@Test
	public void test01() {
		List<String>names = new LinkedList<>();
		names.add("A");
		names.add("B");
		names.add("C");
		
		ListIterator<String> listIterator = names.listIterator();
		System.out.println("Forward Direction Iteration:");
		while(listIterator.hasNext()){
			System.out.println(listIterator.next());			
		}	
		
		// Traversing elements
		System.out.println("Backward Direction Iteration:");
		while(listIterator.hasPrevious()){
			System.out.println(listIterator.previous());			
		}
	}

	@Test
	public void test02(){
		List<String> names = new LinkedList<>();
		names.add("E-1");
		names.add("E-2");
		names.add("E-3");
		names.add("E-n");

		ListIterator<String> namesIterator = names.listIterator();
		System.out.println(namesIterator.hasPrevious());

		while(namesIterator.hasPrevious()){
			System.out.println(namesIterator.previous());
		}
	}

}

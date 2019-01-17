package org.java.core.base.collection.hashSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Java HashSet迭代器是快速失败的，因此如果Set是结构修改的，
 * 它的方法将抛出java.util.ConcurrentModificationException。 
 * 
 * Note that: 不保证HashSet元素是排序.由于HashSet元素顺序不确定
 * ，所以如果Orange是iterator的最后一个元素 ，就不会得到异常，否则就会抛出异常，
 * 因为异常的抛出是由iterator.next()抛出的，所以如果Orange是最后一个元素，那么
 * iterator.next()就不会被调用，所以异常就不会有.
 */
public class HashSetConcurrentModificationExceptionExample {

	public static void main(String[] args) {
		Set<String> fruits = new HashSet<>();
		
		//add example
		fruits.add("Apple");
		fruits.add("Banana");
		fruits.add("Orange");
		fruits.add("Mango");
		
		Iterator<String> iterator = fruits.iterator();
		
		while(iterator.hasNext()){
			String fruit = iterator.next();
			System.out.println("Processing "+fruit);
			
			//wrong way of removing from Set, can throw java.util.ConcurrentModificationException
			if("Orange".equals(fruit)) fruits.remove("Orange");
		}
	}
	
	//我们应该始终使用Iterator方法进行结构修改，如下面的示例代码所示。
	//一下代码不会抛出异常.
//	public static void main(String[] args) {
//		Set<String> fruits = new HashSet<>();
//		
//		fruits.add("Apple");
//		fruits.add("Banana");
//		fruits.add("Orange");
//		fruits.add("Mango");
//		
//		Iterator<String> iterator = fruits.iterator();
//		
//		while(iterator.hasNext()){
//			String fruit = iterator.next();
//			System.out.println("Processing "+fruit);
//			
//			//correct way of structural modification of Set
//			if("Orange".equals(fruit)) iterator.remove();
//		}
//		System.out.println("fruits set after iteration = "+fruits);
//	}


}

package org.java.core.base.collection.set.hashset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Java HashSet�������ǿ���ʧ�ܵģ�������Set�ǽṹ�޸ĵģ�
 * ���ķ������׳�java.util.ConcurrentModificationException�� 
 * 
 * Note that: ����֤HashSetԪ��������.����HashSetԪ��˳��ȷ��
 * ���������Orange��iterator�����һ��Ԫ�� ���Ͳ���õ��쳣������ͻ��׳��쳣��
 * ��Ϊ�쳣���׳�����iterator.next()�׳��ģ��������Orange�����һ��Ԫ�أ���ô
 * iterator.next()�Ͳ��ᱻ���ã������쳣�Ͳ�����.
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
	
	//����Ӧ��ʼ��ʹ��Iterator�������нṹ�޸ģ��������ʾ��������ʾ��
	//һ�´��벻���׳��쳣.
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

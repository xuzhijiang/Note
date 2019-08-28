package org.java.core.base.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 当时用Java集合类的时候，java.util.ConcurrentModificationException是非常
 * 常见的异常, Java Collection类是快速失败的，那个意味着如果在某个线程使用迭代器遍历它时
 * 对它做修改，则iterator.next()将抛出ConcurrentModificationException异常，
 * 
 */
public class ConcurrentModificationException {
	
	public static void main(String[] args) {
		List<String> myList = new ArrayList<String>();

		myList.add("1");
		myList.add("2");
		myList.add("3");
		myList.add("4");
		myList.add("5");

		Iterator<String> it = myList.iterator();
		while (it.hasNext()) {
			String value = it.next();
			System.out.println("List Value:" + value);
			if (value.equals("3"))
				myList.remove(value);
		}

//		Since we are updating the existing key value in the myMap, its size has not
		//been changed and we are not getting ConcurrentModificationException.
		
//		because HashMap keyset is not ordered like list.
		Map<String, String> myMap = new HashMap<String, String>();
		myMap.put("1", "1");
		myMap.put("2", "2");
		myMap.put("3", "3");

		Iterator<String> it1 = myMap.keySet().iterator();
		while (it1.hasNext()) {
			String key = it1.next();
			System.out.println("Map Value:" + myMap.get(key));
			if (key.equals("2")) {
				//不会抛异常
				myMap.put("1", "4");
				// myMap.put("4", "4");//如果不注释掉，会抛异常
			}
		}
	}

}

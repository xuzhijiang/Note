package org.java.core.base.collection.arrayList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Iterator是Java Collections框架中的一个接口。
 * 
 * 如果在迭代时对列表进行任何结构修改，则next（）操作将抛
 * 出ConcurrentModificationException。
 * 
 * 注意： iterator.remove()不会产生异常
 * @author a
 *
 */
public class ArrayListIteratorExample {
	public static void main(String[] args) {

		List<Integer> ints = new ArrayList<>();
		for(int i=0; i<10; i++) ints.add(i);
		
		Iterator<Integer> it = ints.iterator();
		
		//simple iteration
		while(it.hasNext()){
			int x = it.next();
			System.out.print(x + ", ");
		}
		System.out.println("\n"+ints);
		
		//modification of list through iterator
		it = ints.iterator();
		while(it.hasNext()){
			int x = (int) it.next();
			if(x%2 ==0) it.remove();//no exception
		}
		System.out.println(ints);
		
		//changing list structure while iterating
		it = ints.iterator();
		while(it.hasNext()){
			int x = (int) it.next(); //ConcurrentModificationException here
			if(x==5) ints.add(20);
		}
	}
}

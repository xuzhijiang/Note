package org.java.core.base.collection.arrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * 我们可以使用ListIterator在两个方向上遍历列表。 
 * 它允许我们删除以及向列表中添加元素。
 *  您还可以在ListIterator中获取迭代器当前位置。 
 *  用于向后遍历列表并修改列表数据。
 * @author a
 *
 */
public class ArrayListListIteratorExample {
	
	public static void main(String[] args) {

		List<Integer> ints = new ArrayList<>();
		for (int i = 0; i < 10; i++) ints.add(i);
		
		ListIterator<Integer> lit = ints.listIterator(ints.size());
		
		while(lit.hasPrevious()){
			int x = lit.previous();
			System.out.print(x + ", ");
			if(x==5){
				lit.remove();
				lit.add(20);
			}
		}
		System.out.println("\n"+ints);
	}
}

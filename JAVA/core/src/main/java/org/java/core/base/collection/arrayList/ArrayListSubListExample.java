package org.java.core.base.collection.arrayList;

import java.util.ArrayList;
import java.util.List;

/**
 * 当我们将subList方法与列表一起使用时，它返回原始列表的一部分视图。 
 * 
 * 此新列表由原始列表支持，因此任何修改也将反映到original列表。
 *  
 * @author a
 *
 */
public class ArrayListSubListExample {

	public static void main(String[] args) {
		
		List<String> names = new ArrayList<>();
		names.add("Pankaj"); names.add("David");names.add("Lisa");names.add("Meghna");
		
		List<String> first2Names = names.subList(0, 2);
		
		System.out.println(names +" , "+first2Names);
		
		names.set(1, "Kumar");
		//check the output below. :)
		System.out.println(names +" , "+first2Names);
		
		first2Names.add("Megan"); //this is fine
		System.out.println(names +" , "+first2Names); //this is fine
		
		//Let's modify the list size and get ConcurrentModificationException
		names.add("Deepak");
		System.out.println(names +" , "+first2Names); //this line throws exception
		//  新列表中的所有方法首先检查后备列表的实际modCount是否等于其预期值，如果不是，则抛出ConcurrentModificationException。
	}

}

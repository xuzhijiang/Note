package org.java.core.base.collection.arrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 在Java 8中添加了ArrayList removeIf方法。
 * 此方法将删除列表中满足给定谓词的所有元素。
 * @author a
 *
 */
public class ArrayListRemoveIfExample {

	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<>();
		for (int i = 0; i < 10; i++) ints.add(i);
		
		Predicate<Integer> filter = new ArrayListRemoveIfExample(). new MyPredicate();
		
		ints.removeIf(filter);
		
		System.out.println(ints);
		
		//lambda expression, remove elements divisible by 3
		ints.removeIf(x -> {return x %3 == 0;});
		
		System.out.println(ints);
	}

	class MyPredicate implements Predicate<Integer> {

		@Override
		public boolean test(Integer t) {
			return t %2 == 0;
		}
		
	}
}

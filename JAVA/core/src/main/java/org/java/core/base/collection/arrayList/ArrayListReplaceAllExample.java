package org.java.core.base.collection.arrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * 在Java 8中添加了ArrayList replaceAll方法。
 * 当您想要对列表的所有元素应用某些函数时，它非常有用。
 * @author a
 *
 */
public class ArrayListReplaceAllExample {

	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<>();
		for (int i = 0; i < 10; i++) ints.add(i);
		
		//multiply all elements by 10
		UnaryOperator<Integer> operator = new ArrayListReplaceAllExample().new MyUnaryOperator();
		ints.replaceAll(operator);
		System.out.println(ints);
		
		//lambda expression example, multiply by 5
		ints.replaceAll(x -> {return x*5;});
		System.out.println(ints);
	}

	class MyUnaryOperator implements UnaryOperator<Integer>{

		@Override
		public Integer apply(Integer t) {
			return t*10;
		}
		
	}
}

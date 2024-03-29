package org.java.core.base.collection.list.arrayList.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 在Java 8中添加了Java ArrayListSource forEach方法。当您想对所有元素执行相同的操作时，它非常有用。
 * 
 *  方法参数Consumer是一个功能接口，因此我们也可以使用lambda表达式
 */
public class ArrayListForEachExample {
	
	public static void main(String[] args) {
		List<String> stocks = new ArrayList<>();
		stocks.add("Google"); 
		stocks.add("Apple");
		stocks.add("Microsoft"); 
		stocks.add("Facebook");
		
		Consumer<String> consumer = new ArrayListForEachExample().new MyConsumer();
		stocks.forEach(consumer);
		
		//lambda style
		stocks.forEach(x -> {System.out.println("lambda Processed "+x);});
	}

	class MyConsumer implements Consumer<String>{

		@Override
		public void accept(String t) {
			System.out.println("Processing "+t);
		}
		
	}
}

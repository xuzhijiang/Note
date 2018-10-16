package org.java.core.base.collection.set;

import java.util.*;

// 下面是一个简单的示例，说明如何将Java Set转换为Stream并根据我们的要求执行某些操作。
public class SetToStream {

   public static void main(String[] args) {
	Set<String> vowelsSet = new HashSet<>();
	// add example
	vowelsSet.add("a");
	vowelsSet.add("e");
	vowelsSet.add("i");
	vowelsSet.add("o");
	vowelsSet.add("u");
		
	//convert set to stream
	vowelsSet.stream().forEach(System.out::println);
   }
}

package org.java.core.base.collection.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 与List不同，我们不能直接将Java Set转换为数组，因为它不是使用Array实现的。
 * 
 * 所以我们不能使用Arrays类来获得array作为set的视图。 我们可以采用另一种方法。
 * 我们可以使用Arrays.asList（）方法将数组转换为List，然后使用它来创建Set。
 * 
 * 通过使用这种方法，我们可以通过两种方式将Java数组转换为Set。
 */
public class ArrayToSet {
	public static void main(String[] args) {

		String[] vowels = { "a", "e", "i", "o", "u" };

		Set<String> vowelsSet = new HashSet<String>(Arrays.asList(vowels));
		System.out.println(vowelsSet);

		/**
		 * Unlike List, Set is NOt backed(支持) by array, so we can do structural modification
		 * without any issues.
		 */
		vowelsSet.remove("e");
		System.out.println(vowelsSet);
		vowelsSet.clear();
		System.out.println(vowelsSet);
	}
}

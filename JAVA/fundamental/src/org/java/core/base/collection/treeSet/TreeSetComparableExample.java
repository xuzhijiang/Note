package org.java.core.base.collection.treeSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * 现在，我们将创建一个带有Person类对象的有序集。 
 * To，提供自然排序Person类应该具有Comparable接口的实现。
 */
public class TreeSetComparableExample {
	public static void main(String[] args){

		// Create a sorted set with user defined class
		SortedSet<Person> sortedSet = new TreeSet<>();
		sortedSet.add(new Person(1, "Mark"));
		sortedSet.add(new Person(2, "Vispi"));
		sortedSet.add(new Person(3, "Harmony"));
		sortedSet.forEach(System.out::println);

		// 为了提供不同的排序，我们需要在创建有序集时传递自定义比较器实现。
		// 例如，让我们根据Person类的id属性进行排序。
		// we can also provide instance of Comparator implementation instead of lambda
		SortedSet<Person> customOrderedSet = new TreeSet<>((p1, p2) -> p1.id - p2.id);
		customOrderedSet.addAll(sortedSet);
		customOrderedSet.forEach(System.out::println);
		
		// 我们还可以通过传递another collection object or a different sorted set来创建有序集。
		List<Person> listOfPerson = Arrays.asList(new Person(1, "Mark"), new Person(2, "Vispi"), new Person(3, "Harmony"));
		SortedSet<Person> sortedSetFromCollection = new TreeSet<>(listOfPerson);
		SortedSet<Person> copiedSet = new TreeSet<>(sortedSetFromCollection);
		copiedSet.forEach(System.out::println);

		// 我们将通过传递比较器来创建一个有序集。 这里，comparator（）方法将返回相同的比较器：
		SortedSet<Integer> intSet = new TreeSet<>(Comparator.naturalOrder());
		intSet.addAll(Arrays.asList(7,2,1,4,6,5));
		Comparator comparator = intSet.comparator();
		
		// Note that the changes made on subset are reflected on the original set as well.
		// 让我们使用subSet（from，to）方法找到子集。 请注意，对子集所做的更改也会反映在原始集上。
		SortedSet<Integer> subSet = intSet.subSet(2, 5);
		System.out.println(subSet);
		subSet.add(3);
		System.out.println(intSet);

		subSet = intSet.headSet(3);
		System.out.println("Head set");
		System.out.println(subSet);
		
		subSet = intSet.tailSet(3);
		System.out.println("Tail set");
		System.out.println(subSet);
		
		System.out.println("Retrieving lowest and highest elements respectively");
		System.out.println(intSet.first());
		System.out.println(intSet.last());

	}
}

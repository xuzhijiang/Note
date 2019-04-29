package org.java.core.base.collection.set.treeset;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ���ڣ����ǽ�����һ������Person���������򼯡� 
 * To���ṩ��Ȼ����Person��Ӧ�þ���Comparable�ӿڵ�ʵ�֡�
 */
public class TreeSetComparableExample {
	public static void main(String[] args){

		// Create a sorted set with user defined class
		SortedSet<Person> sortedSet = new TreeSet<>();
		sortedSet.add(new Person(1, "Mark"));
		sortedSet.add(new Person(2, "Vispi"));
		sortedSet.add(new Person(3, "Harmony"));
		sortedSet.forEach(System.out::println);

		// Ϊ���ṩ��ͬ������������Ҫ�ڴ�������ʱ�����Զ���Ƚ���ʵ�֡�
		// ���磬�����Ǹ���Person���id���Խ�������
		// we can also provide instance of Comparator implementation instead of lambda
		SortedSet<Person> customOrderedSet = new TreeSet<>((p1, p2) -> p1.id - p2.id);
		customOrderedSet.addAll(sortedSet);
		customOrderedSet.forEach(System.out::println);
		
		// ���ǻ�����ͨ������another collection object or a different sorted set���������򼯡�
		List<Person> listOfPerson = Arrays.asList(new Person(1, "Mark"), new Person(2, "Vispi"), new Person(3, "Harmony"));
		SortedSet<Person> sortedSetFromCollection = new TreeSet<>(listOfPerson);
		SortedSet<Person> copiedSet = new TreeSet<>(sortedSetFromCollection);
		copiedSet.forEach(System.out::println);

		// ���ǽ�ͨ�����ݱȽ���������һ�����򼯡� ���comparator����������������ͬ�ıȽ�����
		SortedSet<Integer> intSet = new TreeSet<>(Comparator.naturalOrder());
		intSet.addAll(Arrays.asList(7,2,1,4,6,5));
		Comparator comparator = intSet.comparator();
		
		// Note that the changes made on subset are reflected on the original set as well.
		// ������ʹ��subSet��from��to�������ҵ��Ӽ��� ��ע�⣬���Ӽ������ĸ���Ҳ�ᷴӳ��ԭʼ���ϡ�
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

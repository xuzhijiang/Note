package org.java.core.base.map;

import java.util.*;
import java.util.Map.Entry;

/**
 * 正确使用Map，只需要正确实现hashCode()和equals()就行了吗？
 *
 * 恐怕还不行。
 *
 * 确切地说，如果使用的是HashMap，只要正确实现hashCode()和equals()就够了。
 *
 * 但是，如果换成TreeMap，正确实现hashCode()和equals()，结果并不一定正确。
 */
public class MapExample {

	public static void main(String[] args) {
		 testHashMap();

		 testTreeMap();
	}

	// 说好的接口不变，实现类随便换现在不管用了？难道是JDK的bug？
	// 先打开JDK的TreeMap文档，注意到这句话：
	// 意思是，Map接口定义了使用equals()判定key是否相等，
	// 但是SortedMap却使用compareTo()来判断key是否相等，而TreeMap是一种SortedMap。
	// 所以，问题出在compareTo()方法上：
	private static void testTreeMap() {

		System.out.println("-------TreeMap------");
		Map<Student, Integer> map = new TreeMap<>();
		map.put(new Student("aa", 99), 99);
		map.put(new Student("bb", 79), 79);
		map.put(new Student("cc", 59), 59);
		System.out.println(map.get(new Student("aa", 99)));
		System.out.println(map.get(new Student("bb", 79)));
		System.out.println(map.get(new Student("cc", 59)));
	}

	private static void testHashMap() {
		System.out.println("-------HashMap------");
		Map<Student, Integer> map = new HashMap<>();
		map.put(new Student("aa", 99), 99);
		map.put(new Student("bb", 79), 79);
		map.put(new Student("cc", 59), 59);
		System.out.println(map.get(new Student("aa", 99)));
		System.out.println(map.get(new Student("bb", 79)));
		System.out.println(map.get(new Student("cc", 59)));
	}


	// 作为key的class
	// Student 实现了Comparable接口后就是可以比较的了
	private static class Student implements Comparable<Student> {
		final String name;
		final int score;

		public Student(String name, int score) {
			this.name = name;
			this.score = score;
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, score);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Student) {
				Student o = (Student) obj;
				return Objects.equals(this.name, o.name) && this.score == o.score;
			}
			return false;
		}

//		@Override
//		public int compareTo(Student o) {
//			return this.score < o.score ? -1 : 1;
//		}

		// 上面这个定义，用来排序是没问题的，但是，没法判断相等。
		// TreeMap根据key.compareTo(anther)==0判断是否相等，而不是equals()。

		// 所以，解决问题的关键是正确实现compareTo()，该相等的时候，必须返回0：
		@Override
		public int compareTo(Student o) {
			int n = Integer.compare(this.score, o.score);
			return n != 0 ? n : this.name.compareTo(o.name);
		}
	}

}

package org.java.core.base.generics;

import java.util.List;

public class GenericWildcard {

	/**
	 * 参数是一个上限有界通配符，这样就可以传入List<Integer> or List<Double>等等.
	 * @param list
	 * @return
	 */
	public static double sum(List<? extends Number> list){
		double sum = 0;
		for(Number n : list){
			sum += n.doubleValue();
		}
		return sum;
	}

	/**
	 * 上限有界通配符 : 比较2个对象，我们要确保接受的2个对象都实现了Comparable接口,extends关键字，后跟其上限
	 * @param t1
	 * @param t2
	 * @param <T>
	 * @return
	 */
	// 这些方法的调用类似于无界方法, 如果我们尝试传入不可比较的类，它会抛出编译时错误
	public static <T extends Comparable> int compare(T t1, T t2){
		return t1.compareTo(t2);
	}

	/**
	 * 无界通配符 Unbounded wildcards
	 * List<?>与使用List<？ extends Object>效果相同
	 * @param list
	 */
	public static void printData(List<?> list){
		for (Object obj : list) {
			System.out.println(list);
		}
	}
	
	/**
	 * 我们使用泛型通配符？和super关键字以及下界类去实现Java泛型下界通配符
	 *
	 * 在这种情况下，我们可以将下限或下限类型的父类型作为参数传递，
	 * java编译器允许将下界对象类型添加到列表中。
	 * @param list
	 */
	// ?是Integer或者Integer的父类
	public static void addInteger(List<? super Integer> list){
		list.add(new Integer(50));
	}
	
}

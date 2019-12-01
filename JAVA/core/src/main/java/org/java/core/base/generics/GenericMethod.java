package org.java.core.base.generics;

public class GenericMethod {

	/**
	 * 展示了如何在方法中使用泛型的语法
	 * @param g1
	 * @param g2
	 * @param <T>
	 * @return
	 */
	public static <T> boolean isEqual(GenericsType<T> g1, GenericsType<T> g2){
		return g1.get().equals(g2.get());
	}

	/**
	 * 用于判断一个元素数组中是包含指定的元素
	 * @param elements
	 * @param element
	 * @param <T>
	 * @return
	 */
	public static <T> boolean contain(T[] elements, T element) {
		for (T e : elements) {
			if (e.equals(element)) {
				return true;
			}
		}
		return false;
	}

}

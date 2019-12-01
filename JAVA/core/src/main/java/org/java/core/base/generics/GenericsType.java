package org.java.core.base.generics;

// T不能是基本数据类型
public class GenericsType<T> {

	private T t;

	public T get() {
		return this.t;
	}

	public void set(T t) {
		this.t = t;
	}

	public GenericsType(T t) {
		this.t = t;
	}
}

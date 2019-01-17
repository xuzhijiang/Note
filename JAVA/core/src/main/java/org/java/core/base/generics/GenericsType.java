package org.java.core.base.generics;

public class GenericsType<T> {

	private T t;
	
	public T get() {
		return this.t;
	}
	
	public void set(T t1) {
		this.t = t1;
	}
	
	/**
	 * 注意在main方法中使用GenericsType类。 我们不需要进行类型转换，
	 * 我们可以在运行时删除ClassCastException。 如果我们在创建时不提供类型，
	 * 编译器将生成警告“GenericsType是原始类型。 
	 * 对泛型类型GenericsType <T>的引用应该参数化“。
	 * 
	 *  当我们不提供类型时，类型变为Object，因此它允许String和Integer对象，
	 *  但我们应该总是试图避免这种情况，因为在处理可能产生运行时错误的原始类型时我们将不得不使用类型转换。
	 *  
	 *  提示：我们可以使用@SuppressWarnings（“rawtypes”）注释来抑制编译器警告.
	 * @param args
	 */
	public static void main(String[] args) {
		GenericsType<String> type = new GenericsType<>();
		type.set("xzj");// valie
		
		// 泛型如果没有参数化，那么默认就是Object类型的,这种情况可能造成运行时异常
		GenericsType type1 = new GenericsType(); // raw type
		type1.set("Pankaj"); // valid
		type1.set(10); // valid and autoboxing support
	}
}

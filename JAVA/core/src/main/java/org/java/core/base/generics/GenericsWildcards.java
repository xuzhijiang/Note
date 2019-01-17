package org.java.core.base.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Java泛型通配符
 * 
 * Question mark (?) 是泛型中的通配符，表示未知类型。
 * 
 *  通配符可以用作参数，字段或局部变量的类型，有时也可以用作返回类型。
 *  
 *   我们在调用泛型方法或实例化泛型类时不能使用通配符。 
 *   在接下来的部分中，我们将了解上边界通配符，下边界通配符和通配符捕获。
 *   （upper bounded wildcards, lower bounded wildcards, and wildcard capture.）
 * 
 * Java泛型上限有界通配符upper bounded wildcards
 *  上限有界通配符用于放宽方法中变量类型的限制。
 * 
 *  Java泛型无界通配符 Unbounded wildcards
 *   有时我们希望我们的泛型方法适用于所有类型，在这种情况下可以使用无界通配符。 
 *  List<?> 与使用List<？ extends Object>效果相同
 *  
 *  Java Generics下限通配符（lower bounded Wildcard）
 *  
 */
public class GenericsWildcards {

	public static void main(String[] args) {
		List<Integer> ints = new ArrayList<>();
		ints.add(3); ints.add(5); ints.add(10);
		double sum = sum(ints);
		System.out.println("Sum of ints="+sum);
		
		addIntegers(new ArrayList<Object>());// lower bound wildcard
	}

	/**
	 * 参数是一个上限有界通配符，这样就可以传入List<Integer> or List<Double>等等.
	 * 
	 * 我们可以使用上限类Number的所有方法
	 * @param list
	 * @return
	 */
	public static double sum(List<? extends Number> list){
		//不允许向list中添加除null之外的任何对象,如果添加的话，程序将在编译时报错.
		//list.add(10); //error
		double sum = 0;
		for(Number n : list){
			sum += n.doubleValue();
		}
		return sum;
	}
	
	//类似于上限有界通配符， 与上限列表类似，我们不允许向列表中添加任何内容。
	public static void printData(List<?> list){
		for (Object obj : list) {
			System.out.print(obj + "::");
		}
	}
	
	// Java Generics Lower bounded Wildcard
	/**
	 * 我们使用泛型通配符？和super关键字以及下界类去实现Java泛型下界通配符
	 *  We use generics wildcard (?) with super keyword 
	 *  and lower bound class to achieve this.
	 * 
	 * 在这种情况下，我们可以将下限或下限类型的父类型作为参数传递，
	 * java编译器允许将下界对象类型添加到列表中。
	 * @param list
	 */
	public static void addIntegers(List<? super Integer> list){
		list.add(new Integer(50));
	}
	
}

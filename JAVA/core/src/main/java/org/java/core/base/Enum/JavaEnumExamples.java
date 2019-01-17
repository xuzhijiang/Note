package org.java.core.base.Enum;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public class JavaEnumExamples {

	public static void main(String[] args) throws IOException {

		usingEnumMethods();

		usingEnumValueOf();

		usingEnumValues();

		usingEnumInSwitch(ThreadStatesEnum.START);
		usingEnumInSwitch(ThreadStatesEnum.DEAD);

		usingEnumMap();

		usingEnumSet();

	}

	/**
	 * usingEnumSet（）方法显示了java.util.EnumSet的使用，
	 * 这是用于枚举类型的Set实现。枚举集set中的所有元素必须来自单个枚举类型，
	 * 该类型在创建集时显式或隐式指定。 EnumSet未同步，不允许使用null元素。
	 * 它还提供了一些有用的方法，如copyOf（Collection <E> c），
	 * （E first，E ... rest）和complementOf（EnumSet <E> s）。
	 */
	private static void usingEnumSet() {
		EnumSet<ThreadStatesEnum> enumSet = EnumSet.allOf(ThreadStatesEnum.class);
		
		for (ThreadStatesEnum tsenum : enumSet) {
			System.out.println("Using EnumSet, priority = " + tsenum.getPriority());
		}
	}

	/**
	 * usingEnumMap（）方法显示了java.util.EnumMap的使用，
	 * 它是Java 1.5 Collections Framework中引入的。 EnumMap是用于枚举类型键的Map实现。
	 * 枚举映射中的所有键 必须来自创建映射时显式或隐式指定的单个枚举类型。我们不能使用null作为EnumMap的键，
	 * 并且EnumMap不同步。
	 */
	private static void usingEnumMap() {
		EnumMap<ThreadStatesEnum, String> enumMap = new EnumMap<ThreadStatesEnum, String>(ThreadStatesEnum.class);
		enumMap.put(ThreadStatesEnum.START, "Thread is started");
		enumMap.put(ThreadStatesEnum.RUNNING, "Thread is running");
		enumMap.put(ThreadStatesEnum.WAITING, "Thread is waiting");
		enumMap.put(ThreadStatesEnum.DEAD, "Thread is dead");

		Set<ThreadStatesEnum> keySet = enumMap.keySet();
		for (ThreadStatesEnum key : keySet) {
			System.out.println("key=" + key.toString() + ":: value=" + enumMap.get(key));
		}
	}

	/**
	 * usingEnumInSwitch（）方法显示了如何在switch case中使用枚举常量。
	 * @param th
	 */
	private static void usingEnumInSwitch(ThreadStatesEnum th) {
		switch (th) {
		case START:
			System.out.println("START thread");
			break;
		case WAITING:
			System.out.println("WAITING thread");
			break;
		case RUNNING:
			System.out.println("RUNNING thread");
			break;
		case DEAD:
			System.out.println("DEAD thread");
		}
	}

	/**
	 * usingEnumValues（）方法显示了values（）方法的用法，该方法返回一个数组，
	 * 该数组按照声明的顺序包含枚举的所有值。请注意，此方法由java编译器为每个枚举自动生成。
	 * 您将在java.util.Enum类中找不到values（）实现。
	 */
	private static void usingEnumValues() {
		ThreadStatesEnum[] thArray = ThreadStatesEnum.values();

		for (ThreadStatesEnum th : thArray) {
			System.out.println("usingEnumValues ---- " + th.toString() + "::priority=" + th.getPriority());
		}
	}

	/**
	 * usingEnumValueOf（）显示了java.util.Enum valueOf（enumType，name）的用法，
	 * 通过它我们可以从String创建一个枚举对象。如果指定的枚举类型没有具有指定名称的常量，
	 * 或者指定的类对象不表示枚举类型，则抛出IllegalArgumentException。
	 * 如果任何参数为null，它也会抛出NullPointerException。
	 */
	private static void usingEnumValueOf() {
		ThreadStatesEnum th = Enum.valueOf(ThreadStatesEnum.class, "START");
		System.out.println("usingEnumValueOf th priority=" + th.getPriority());
	}

	/**
	 * usingEnumMethods（）显示了如何创建枚举对象以及如何使用它的方法。
	 * 它还显示了使用setPriority（int i）方法来更改枚举的变量。
	 * @throws IOException
	 */
	private static void usingEnumMethods() throws IOException {
		ThreadStatesEnum thc = ThreadStatesEnum.DEAD;
		System.out.println("priority is:" + thc.getPriority());

		thc = ThreadStatesEnum.DEAD;
		System.out.println("Using overriden method." + thc.toString());

		thc = ThreadStatesEnum.START;
		System.out.println("Using overriden method." + thc.toString());
		thc.setPriority(10);
		System.out.println("Enum Constant variable changed priority value=" + thc.getPriority());
		thc.close();
	}

}

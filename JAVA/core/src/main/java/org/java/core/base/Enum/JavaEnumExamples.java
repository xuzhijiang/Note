package org.java.core.base.Enum;

import org.junit.Test;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public class JavaEnumExamples {

	@Test
	public void usingEnumSet() {
		// java.util.EnumSet是枚举类型的Set实现
		EnumSet<ThreadStatesEnum> enumSet = EnumSet.allOf(ThreadStatesEnum.class);
		
		for (ThreadStatesEnum e : enumSet) {
			System.out.println("priority = " + e.getPriority());
		}
	}

	@Test
	public void usingEnumMap() {
		// java.util.EnumMap,EnumMap是用于枚举类型键的Map实现
		EnumMap<ThreadStatesEnum, String> enumMap = new EnumMap<>(ThreadStatesEnum.class);
		enumMap.put(ThreadStatesEnum.START, "Thread is started");
		enumMap.put(ThreadStatesEnum.RUNNING, "Thread is running");
		enumMap.put(ThreadStatesEnum.WAITING, "Thread is waiting");
		enumMap.put(ThreadStatesEnum.DEAD, "Thread is dead");

		Set<ThreadStatesEnum> keySet = enumMap.keySet();
		for (ThreadStatesEnum key : keySet) {
			System.out.println("key=" + key.toString() + ":: value=" + enumMap.get(key));
		}
	}

	@Test
	public void usingEnumValues() {
		ThreadStatesEnum[] thArray = ThreadStatesEnum.values();

		for (ThreadStatesEnum th : thArray) {
			System.out.println("usingEnumValues ---- " + th.toString() + "::priority=" + th.getPriority());
		}
	}

	@Test
	public void usingEnumValueOf() {
		ThreadStatesEnum th = Enum.valueOf(ThreadStatesEnum.class, "START");
		System.out.println("usingEnumValueOf th priority=" + th.getPriority());
		System.out.println("getDetail: " + th.getDetail());
	}

	@Test
	public void usingEnumMethods() throws IOException {
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

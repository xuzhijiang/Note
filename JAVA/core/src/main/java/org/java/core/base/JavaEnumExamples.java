package org.java.core.base;

import org.junit.Test;

import java.io.Closeable;
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

enum ThreadStatesEnum implements Closeable {

	START(1) {
		@Override
		public String toString() {
			return "START implementation. Priority=" + getPriority();
		}

		// getDetail是抽象方法，所以必须要实现
		@Override
		public String getDetail() {
			return "START";
		}
	},RUNNING(2) {
		@Override
		public String getDetail() {
			return "RUNNING";
		}
	},WAITING(3) {
		@Override
		public String getDetail() {
			return "WAITING";
		}
	},DEAD(4) {
		@Override
		public String getDetail() {
			return "DEAD";
		}
	};

	private int priority;

	public abstract String getDetail();

	// Enum的构造器总是private的，所以不用显示的添加private
	ThreadStatesEnum(int i) {
		priority = i;
	}

	// Enum可以有方法
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int p) {
		this.priority = p;
	}

	// Enum可以覆写函数
	@Override
	public String toString() {
		return "Default ThreadStatesConstructors implementation. Priority=" + getPriority();
	}

	@Override
	public void close() throws IOException {
		System.out.println("Close of Enum");
	}
}

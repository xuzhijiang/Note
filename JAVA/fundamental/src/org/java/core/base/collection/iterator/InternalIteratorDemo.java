package org.java.core.base.collection.iterator;

import java.util.LinkedList;
import java.util.List;

public class InternalIteratorDemo {
	
	public static void main(String[] args) {
		List<String> names = new LinkedList<>();
		names.add("Rams");
		names.add("Posa");
		names.add("xzj");
		
		// 增强的for循环在内部使用Iterator对象
		for(String name : names) {
			System.out.println(name);
		}
	}
}

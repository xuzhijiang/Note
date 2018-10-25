package org.java.core.base.collection.hashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

// 如果指定的键存在且值不为null，则Java HashMap computeIfPresent方法将重新计算该值。
// 如果函数返回null，则删除映射。
public class HashMapComputeIfPresentExample {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("1", "10");
		map.put("2", "20");
		map.put(null, "100");
		map.put("10", null);

		System.out.println("map before computeIfPresent = " + map);
		BiFunction<String, String, String> function = new MyBiFunction1();
		for (String key : map.keySet()) {
			map.computeIfPresent(key, function);
		}
		
		System.out.println("map after computeIfPresent = " + map);
		map.computeIfPresent("1", (k,v) -> {return null;}); // mapping will be removed
		System.out.println("map after computeIfPresent = " + map);

	}

}

class MyBiFunction1 implements BiFunction<String, String, String> {

	@Override
	public String apply(String t, String u) {
		return t + u;
	}

}

package org.java.core.base.map.hashmap;

import org.junit.Test;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.AbstractMap;
import java.util.function.BiFunction;

public class HashMapDemo {

	@Test
	public void testHashMapMethods() {
		HashMap<String, String> map = new HashMap<String, String>();
		// 键不能重复，值可以重复
		map.put("san", "张三");
		map.put("si", "李四");
		map.put("wu", "王五");
		map.put("wang", "老王");
		map.put("wang", "老王2");// 老王被覆盖
		map.put("lao", "老王");

		System.out.println("after map.size()："+map.size());
		System.out.println("after map.isEmpty()："+map.isEmpty());
		System.out.println(map.remove("san"));
		System.out.println("after map.remove()："+map);
		System.out.println("after map.get(si)："+map.get("si"));
		System.out.println("after map.containsKey(si)："+map.containsKey("si"));
		System.out.println("after containsValue(李四)："+map.containsValue("李四"));
		System.out.println(map.replace("si", "李四2"));
		System.out.println("after map.replace(si, 李四2):"+map);
	}

	@Test
	public void testHashMapCompute() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", "2");
		map.put(null, "10");
		map.put("10", null);

		System.out.println("map before compute = "+map);
		for (String key : map.keySet()) {
			map.compute(key, (k,v) -> k+v);
		}
		map.compute("5", (k,v) -> k+v); //key not present, v = null
		System.out.println("map after compute = "+map);
	}

	/**
	 *  HashMap replaceAll方法可用于将每个条目的值替换为在该条目上调用给定函数的结果。
	 *  在Java 8中添加了此方法，我们可以将lambda表达式用于此方法参数。
	 */
	@Test
	public void testHashMapReplaceAll() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		map.put(null, "c");

		System.out.println("map before replaceAll = " + map);
		map.replaceAll((t, u) -> {
			return t != null ? t + u : u;
		});
		System.out.println("map after replaceAll = " + map);

		map.replaceAll((k, v) -> {
			if (k != null) return k + v;
			else return v;});
		System.out.println("map after replaceAll lambda expression = " + map);
	}
}

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

public class HashMapTest {

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
		System.out.println("-------直接输出hashmap:-------");
		System.out.println(map);

		/**
		 * 遍历HashMap的3种方式
		 *
		 * **强烈建议**使EntrySet 进行遍历,因为EntrySet可以把 key value 同时取出，
		 * 第一种还得需要通过 key 取一次 value，效率较低,
		 * 第三种需要 `JDK1.8` 以上，通过外层遍历 table(内部数组)，内层遍历链表或红黑树。
		 */
		// 第一种
		System.out.println("-------foreach获取Map中所有的键:------");
		 // keySet方法返回HashMap中键的Set集合。 此Set由HashMap支持，HashMap中的任何更改都反映在Set中，反之亦然。
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.print(key+"  ");
		}
		System.out.println("-------得到key的值的同时得到key所对应的值:-------");
		Iterator<String> keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String next = keyIterator.next();
			System.out.println("key: " + next + ", value: " + map.get(next));
		}
		System.out.println();//换行
		// 获取Map中所有值
		System.out.println("-------foreach获取Map中所有的值:------");
		/**
		 * values方法返回Map中value的Collection视图。此集合由HashMap支持，
		 * 因此HashMap中的任何更改都将反映在值集合中，
		 */
		Collection<String> values = map.values();
		for (String value : values) {
			System.out.print(value+"  ");
		}
		System.out.println();//换行

		// 第二种
		// 当我调用put(key,value)方法的时候，首先会把key和value封装到
		// Entry这个静态内部类对象中，把Entry对象再添加到数组中，所以我们想获取
		// map中的所有键值对，我们只要获取数组中的所有Entry对象，接下来
		// 调用Entry对象中的getKey()和getValue()方法就能获取键值对了
		Set<java.util.Map.Entry<String, String>> entrys = map.entrySet();
		for (java.util.Map.Entry<String, String> entry : entrys) {
			System.out.println(entry.getKey() + "--" + entry.getValue());
		}
		Iterator<Map.Entry<String, String>> iterator = entrys.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> next = iterator.next();
			System.out.println("key: " + next.getKey() + ", value: " + next.getValue());
		}

		// 第三种
		map.forEach((key, value) -> {
			System.out.println("key=" + key + " value=" + value);
		});

		/**
		 * HashMap其他常用方法
		 */
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

	/**
	 * absent: 缺席
	 */
	@Test
	public void testHashMapPutIfAbsent() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", null);
		map.put(null, "100");

		System.out.println("map before putIfAbsent = "+map);
		System.out.println("putIfAbsent returns: "+map.putIfAbsent("1", "4"));
		System.out.println("map after putIfAbsent = "+map);

		System.out.println("map before putIfAbsent = "+map);
		System.out.println("putIfAbsent returns: "+map.putIfAbsent("3", "3"));
		System.out.println("map after putIfAbsent = "+map);
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
		map.replaceAll(new MyBiFunction());
		System.out.println("map after replaceAll = " + map);

		// replaceAll using lambda expressions
		map.replaceAll((k, v) -> {
			if (k != null) return k + v;
			else return v;});
		System.out.println("map after replaceAll lambda expression = " + map);

	}

	private class MyBiFunction implements BiFunction<String, String, String> {

		@Override
		public String apply(String t, String u) {
			if (t != null)
				return t + u;
			else
				return u;
		}

	}
}

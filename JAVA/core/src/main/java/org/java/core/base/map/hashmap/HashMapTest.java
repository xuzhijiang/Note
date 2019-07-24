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
		Map<String, String> map = new HashMap<>();

		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", null); // null value
		map.put(null, "100"); // null key

		System.out.println("Key = 3, Value = " + map.get("3"));
		System.out.println("Key = 5, Value=" + map.getOrDefault("5", "Default Value"));

		System.out.println("null key exists? " + map.containsKey(null) + ",100 value Exists? " + map.containsValue("100"));

		System.out.println("map size=" + map.size());

		Map<String, String> map1 = new HashMap<>();
		map1.putAll(map);
		System.out.println("map1 mappings= " + map1);

		String nullKeyValue = map1.remove(null);
		System.out.println("map1 null key value = " + nullKeyValue);
		System.out.println("map1 after removing null key = " + map1);

		Collection<String> values = map.values();
		System.out.println("map values = " + values);

		map.clear();
		System.out.println("map is empty=" + map.isEmpty());
	}

	/**
	 * 负载因子是用于计算何时HashMap将被用于重新散列，以及桶的大小何时应该扩容的一个参数.
	 **/
	@Test
	public void testHashMapConstructor() {
		// 此构造函数将创建一个空的HashMap，其默认初始容量为16，加载因子为0.75。
		// 当 HashMap 的 size > 16 * 0.75 时就会发生扩容
		Map<String, String> map1 = new HashMap<>();

		// 指定初始容量和默认0.75加载因子。 
		// 如果你提前知道元素总数，那么就可以提前指定容量大小,避免重新散列消耗性能
		Map<String, String> map2 = new HashMap<>(2^5);

		// 指定容量大小和加载因子,一般情况下加载因子不要随意改
		// 因为负载系数0.75可以在空间和时间成本之间进行良好的权衡。
		// 默认load factor 0.75提供了空间和时间复杂度之间的良好折衷。
		// 但你可以根据需要将其设置为不同的值。如果你想节省空间，
		// 那么你可以将它的值增加到0.80或0.90，但是get/put操作将花费更多时间。
		Map<String, String> map3 = new HashMap<>(32,0.80f);

		Map<String,String> map4 = new HashMap<>(map1);
	}

	/**
	 * 遍历HashMap的3种方式
	 *
	 * **强烈建议**使EntrySet 进行遍历,因为EntrySet可以把 key value 同时取出，
	 * 第二种还得需要通过 key 取一次 value，效率较低,
	 * 第三种需要 `JDK1.8` 以上，通过外层遍历 table(内部数组)，内层遍历链表或红黑树。
	 */
	@Test
	public void testHashMapTraverse() {
		Map<String, Integer> map = new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		map.put("d", 4);

		// 第一种
		Set<Map.Entry<String, Integer>> entries = map.entrySet();
		for (Map.Entry<String, Integer> entry : entries) {
			System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
		}

		Iterator<Map.Entry<String, Integer>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> next = iterator.next();
			System.out.println("key: " + next.getKey() + ", value: " + next.getValue());
		}

		// 第二种
		Iterator<String> keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String next = keyIterator.next();
			System.out.println("key: " + next + ", value: " + map.get(next));
		}

		// 第三种
		map.forEach((key, value) -> {
			System.out.println("key=" + key + " value=" + value);
		});
	}

	/**
	 * keySet方法返回HashMap中键的Set视图。 此Set视图由HashMap支持，
	 * HashMap中的任何更改都反映在Set中，反之亦然。
	 */
	@Test
	public void testHashMapKeySet() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", "c");

		Set<String> keySet = map.keySet();
		System.out.println(keySet);

		map.put("4", "d");
		System.out.println(keySet); // keySet is backed by Map

		keySet.remove("1");
		System.out.println(map); // map is also modified

		Set<String> hashSet = new HashSet<>(map.keySet()); // copies the key to new Set
		map.put("5", "5");
		System.out.println(hashSet); // hashSet is not modified

		System.out.println("----------");
		for (String key : keySet) {
			System.out.println(key);
		}
		System.out.println("----------");
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	/**
	 * values方法返回Map中value的Collection视图。此集合由HashMap支持，
	 * 因此HashMap中的任何更改都将反映在值集合中，
	 */
	@Test
	public void testHashMapValues() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "a");
		map.put("2", "b");
		map.put("3", null);
		map.put("4", null);
		map.put(null, "d");

		Collection<String> values = map.values();
		System.out.println("map values = " + values);

		map.remove(null);
		System.out.println("map values after removing null key = " + values);

		map.put("5", "5");
		System.out.println("map values after put = " + values);

		System.out.println(map);

		values.remove("a"); // changing values collection
		System.out.println(map); // updates in map too

		System.out.println("---------");

		//values的遍历方式一
		for (String v : values) {
			System.out.println(v);
		}

		System.out.println("-----------");

		//values的遍历方式二
		Iterator<String> iterator = values.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

	/**
	 *  entrySet method returns the Set view of mappings.
	 *  This entrySet is backed by HashMap, so any changes
	 *  in map reflects in entry set and vice versa.
	 */
	@Test
	public void testHashMapEntrySet() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", null);
		map.put(null, "100");

		Set<Map.Entry<String,String>> entrySet = map.entrySet();
		Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();

		System.out.println("map before processing = "+map);
		System.out.println("entrySet before processing = "+entrySet);

		for (Map.Entry<String, String> entry : entrySet) {
			System.out.println("key: " + entry.getKey());
		}

		while (iterator.hasNext()) {
			Map.Entry<String, String> next = iterator.next();
			if(next.getKey() == null) iterator.remove();
		}

		System.out.println("map after processing = "+map);
		System.out.println("entrySet after processing = "+entrySet);

		Map.Entry<String, String> simpleEntry = new AbstractMap.SimpleEntry<>("1","1");
		entrySet.remove(simpleEntry);
		System.out.println("map after removing Entry = "+map);
		System.out.println("entrySet after removing Entry = "+entrySet);
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

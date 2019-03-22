package org.java.core.base.ObjectClass.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

// 因为Object hashCode（）方法用于查找存储桶(bucket)以查找key。
// 由于我们无法访问HashMap键并且我们再次创建密钥来检索数据，
// 因此您会注意到两个对象的哈希码值不同，因此找不到值。

// 所以我们使用eclipse的自带功能自动生成equals（）和hashCode（）
public class HashingTest {

	public static void main(String[] args) {
		Map<DataKey, Integer> hm = getAllData();

		DataKey dk = new DataKey();
		dk.setId(1);
		dk.setName("xzj");
		System.out.println(dk.hashCode());

		Integer value = hm.get(dk);

		System.out.println(value);

	}

	private static Map<DataKey, Integer> getAllData() {
		Map<DataKey, Integer> hm = new HashMap<>();

		DataKey dk = new DataKey();
		dk.setId(1);
		dk.setName("xzj");
		System.out.println(dk.hashCode());

		hm.put(dk, 10);

		return hm;
	}

}

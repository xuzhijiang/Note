package org.java.core.base.collection.PriorityQueue;

// 我们的自定义类Customer不提供任何类型的排序，
// 因此当我们尝试将它与Priority Queue一起使用时，我们应该为它提供一个比较器对象。
public class Customer {
	
	private int id;
	private String name;
	
	public Customer(int i, String n){
		this.id=i;
		this.name=n;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}

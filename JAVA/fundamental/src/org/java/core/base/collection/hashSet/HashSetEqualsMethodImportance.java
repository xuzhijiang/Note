package org.java.core.base.collection.hashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * HashSet利用HashMap存储它的元素。 当您尝试添加元素时，
 * HashSet使用equals（）和hashCode（）方法检查重复元素。 
 * 让我们看看如果Set中存储的对象没有提供equals（）方法实现会发生什么。
 * 
 * 所以看起来我们能够在Set中存储重复的元素。实际上并非如此，因为Emp类没有定
 * 义equals（）方法，所以使用了Object类equals（）方法实现。 
 * Object类定义了equals（）方法:
 * public boolean equals(Object obj) { return (this == obj);}
 * 因此，在添加新元素时，正在检查对象引用而不是内容。因此，我们有具有重复内容的对象，但是它们具有不同的引用。
 */
public class HashSetEqualsMethodImportance {

	public static void main(String[] args) {

		Set<Emp> emps = new HashSet<>();
		emps.add(new Emp(1,"Pankaj"));
		emps.add(new Emp(2, "David"));
		emps.add(new Emp(1, "Pankaj"));
		
		System.out.println(emps);
	}

}

class Emp {
	private String name;
	private int id;

	public Emp(int i, String n) {
		this.id = i;
		this.name = n;
	}
	
	@Override
	public String toString(){
		return "{"+id+","+name+"}";
	}
}

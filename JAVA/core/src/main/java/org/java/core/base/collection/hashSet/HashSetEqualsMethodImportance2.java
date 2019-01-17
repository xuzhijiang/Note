package org.java.core.base.collection.hashSet;

import java.util.HashSet;
import java.util.Set;

/**
 * 让我们看看当我们在Emp类中定义hashCode（）和equals（）方法时会发生什么。
 * 
 * 请注意，当我们尝试添加元素时，HashSet能够检查重复项。
 * 但是我们可以使用setter方法更改对象值并使其重复。
 * It worked because there is no operation done on Set. 
 * This is why Immutable objects works better with Set and Map.
 * 它奏效了因为在Set上没有完成任何操作，这就是为什么不可变对象在使用Set和Map是更好.
 */
public class HashSetEqualsMethodImportance2 {

	public static void main(String[] args) {

		Set<Emp2> emps = new HashSet<>();
		emps.add(new Emp2(1,"Pankaj"));
		emps.add(new Emp2(2, "David"));
		emps.add(new Emp2(1, "Pankaj"));
		
		System.out.println(emps);
		
		Emp2 e = new Emp2(3, "Lisa");
		emps.add(e);
		System.out.println(emps);
		
		//set values to make it duplicate
		e.setId(1);
		System.out.println(emps);
		e.setName("Pankaj");
		System.out.println(emps);
	}

}

class Emp2 {
	private String name;
	private int id;

	public Emp2(int i, String n) {
		this.setId(i);
		this.setName(n);
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof Emp2)) return false;
		Emp2 e = (Emp2) obj;
		if(e.getId() == this.getId() && this.getName().equals(e.getName())) return true;
		return false;
	}
	
	@Override
	public int hashCode(){
		return getId();
	}
	
	@Override
	public String toString(){
		return "{"+getId()+","+getName()+"}";
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

}

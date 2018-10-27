package org.java.core.base.collection.iterator;

import java.util.Iterator;

public class EmployeesTester {
	public static void main(String[] args) {
		Employees emps = new Employees();
		for(Employee emp : emps) {
			System.out.println(emp);
		}
		
		Employees emps2 = new Employees();
		Iterator<Employee> iterator = emps2.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
}

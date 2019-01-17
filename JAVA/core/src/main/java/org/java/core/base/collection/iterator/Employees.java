package org.java.core.base.collection.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Employees implements Iterable<Employee>{

	private List<Employee> emps = null;

	public Employees() {
		emps = new ArrayList<>();
		emps.add(new Employee(1001,"Rams","Lead", 250000L));
		emps.add(new Employee(1002,"Posa","Dev", 150000L));
		emps.add(new Employee(1003,"Chinni","QA", 150000L));
	}
	
	@Override
	public Iterator<Employee> iterator() {
		return emps.iterator();
	}
}

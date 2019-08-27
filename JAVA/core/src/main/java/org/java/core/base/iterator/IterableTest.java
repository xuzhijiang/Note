package org.java.core.base.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IterableTest {

	public static void main(String[] args) {
		EmployeeContainer comtainer = new EmployeeContainer();
		// Employees实现了Iterable接口,所以就可以使用增强for循环.
		for(Employee emp : comtainer) {
			System.out.println(emp);
		}
		
		Iterator<Employee> iterator = comtainer.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}

	public static class EmployeeContainer implements Iterable<Employee>{

		private List<Employee> emps = null;

		public EmployeeContainer() {
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

	public static class Employee {

		private int empid;
		private String ename;
		private String designation;
		private double salary;

		public Employee(int empid,String ename,String designation,double salary){
			this.empid = empid;
			this.ename = ename;
			this.designation = designation;
			this.salary = salary;
		}

		public int getEmpid() {
			return empid;
		}

		public String getEname() {
			return ename;
		}

		public String getDesignation() {
			return designation;
		}

		public double getSalary() {
			return salary;
		}

		@Override
		public String toString(){
			return empid + "\t" + ename + "\t" + designation + "\t" + salary;
		}

	}

}

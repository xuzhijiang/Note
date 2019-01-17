package org.java.core.advanced.DesignPatterns.creational.prototype;

public class PrototypePatternTest {

	public static void main(String[] args) throws CloneNotSupportedException {
		Employees emps = new Employees();
		emps.loadData();
		
		//Use the clone method to get the Employee object
		Employees empsNew = (Employees) emps.clone();
		Employees empsNew1 = (Employees) emps.clone();
		empsNew.getEmpList().add("John");
		empsNew1.getEmpList().remove("xzj");
		
		System.out.println("emps List: "+emps.getEmpList());
		System.out.println("empsNew List: "+empsNew.getEmpList());
		System.out.println("empsNew1 List: "+empsNew1.getEmpList());
	}
}

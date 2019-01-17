package org.java.core.base.collection.iterator;


public class Employee {

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

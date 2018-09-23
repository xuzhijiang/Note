package org.java.core.base.serialization;

import java.io.Serializable;

/**
 * 注意到这是一个简单的Java bean,有一些属性和getter setter方法，
 * 如果你想要一个对象属性不被序列化成流，你可以使用transient关键字，(transient: 暂时的)
 * 像我在salary变量上做的。
 */
public class Employee implements Serializable{


	//可以使用serialver命令，在控制台生成: serialver -classpath . com.journaldev.serialization.Employee
	//注意后面的路径是bin下生成的字节码文件.当然也可以使用IDE自动生成.
	private static final long serialVersionUID = 6115687027597651376L;
	
	private static final boolean FLAG = true;
	private String name;
	private int id;
	transient private int salary;
	private String password;
	
	@Override
	public String toString(){
		return "Employee{name="+name+",id="+id+",salary="+salary+"}";
	}
	
	//getter and setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

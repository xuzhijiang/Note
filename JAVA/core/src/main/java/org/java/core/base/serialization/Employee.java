package org.java.core.base.serialization;

import java.io.Serializable;

public class Employee implements Serializable{

	private static final long serialVersionUID = 6115687027597651376L;
	
	private static final String SECRET_STR = "!x*13)$ln&(i$z.i";

    private String name;

    private int id;

    private transient int salary;

    private transient String password;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", salary=" + salary +
                ", password='" + password + '\'' +
                '}';
    }

    public Employee(String name, int id, int salary, String password) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        this.password = password;
    }

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

package org.java.core.advanced.ComparableAndComparator;

import java.util.Comparator;

public class FinalEmployee implements Comparable<FinalEmployee>{

	 private int id;
    private String name;
    private int age;
    private long salary;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public long getSalary() {
        return salary;
    }

    public FinalEmployee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    
    @Override
    //this is required to print the user friendly information about the Employee
    public String toString() {
        return "[id=" + this.id + ", name=" + this.name + ", age=" + this.age + ", salary=" +
                this.salary + "]";
    }
	    
    @Override
    public int compareTo(FinalEmployee emp) {
        //let's sort the employee based on id in ascending order
        //returns a negative integer, zero, or a positive integer as this employee id
        //is less than, equal to, or greater than the specified object.
        return (this.id - emp.id);
    }
    
    // 以匿名类的方式实现Comparator接口

    /**
     * Comparator to sort employees list or array in order of Salary
     */
    public static Comparator<FinalEmployee> SalaryComparator = new Comparator<FinalEmployee>() {

        @Override
        public int compare(FinalEmployee e1, FinalEmployee e2) {
            return (int) (e1.getSalary() - e2.getSalary());
        }
    };

    /**
     * Comparator to sort employees list or array in order of Age
     */
    public static Comparator<FinalEmployee> AgeComparator = new Comparator<FinalEmployee>() {

        @Override
        public int compare(FinalEmployee e1, FinalEmployee e2) {
            return e1.getAge() - e2.getAge();
        }
    };

    /**
     * Comparator to sort employees list or array in order of Name
     */
    public static Comparator<FinalEmployee> NameComparator = new Comparator<FinalEmployee>() {

        @Override
        public int compare(FinalEmployee e1, FinalEmployee e2) {
            return e1.getName().compareTo(e2.getName());
        }
    };
}

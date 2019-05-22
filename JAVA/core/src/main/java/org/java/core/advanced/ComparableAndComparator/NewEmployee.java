package org.java.core.advanced.ComparableAndComparator;

public class NewEmployee implements Comparable<NewEmployee> {

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

    @Override
    public String toString() {
        return "NewEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }

    public NewEmployee(int id, String name, int age, int salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public int compareTo(NewEmployee emp) {
        // 基于id升序排序
        // 返回负数，0，正数，代表小于，等于，大于emp对象
        return (this.id - emp.id);
    }

}

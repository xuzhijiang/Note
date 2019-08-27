package org.java.core.base.ComparableAndComparator;

import org.junit.Test;
import java.util.*;

// 1. 对于Comparable，一个类需要实现它，才能使用Arrays.sort（）或Collection.sort（）进行排序,(使用的是compareTo方法中的比较策略)
// 2. 而对于使用Comparator，我们不需要在类中进行任何更改,我们只需要单独定义Comparator,然后提供给Arrays.sort()我们使用的比较策略即可.
// 3. 带有Comparator参数的Collections.sort（）方法遵循策略模式
public class JavaObjectSorting {

    // Employee没有实现Comparable接口,所以不能使用Arrays.sort()进行排序
    @Test
    public void testArraysSortThrowsException() {
        try {
            Employee[] empArr = new Employee[4];
            empArr[0] = new Employee(10, "Mikey", 25, 10000);
            empArr[1] = new Employee(20, "Arun", 29, 20000);
            empArr[2] = new Employee(5, "Lisa", 35, 5000);
            empArr[3] = new Employee(1, "Pankaj", 32, 50000);
            // 当运行到这里的时候会抛出java.lang.ClassCastException:
            // org.java.core.base.ComparableAndComparator.Employee cannot be cast to java.lang.Comparable
            // 原因是Employee没有实现Comparable接口
            Arrays.sort(empArr);
            System.out.println("Default Sorting of EmployeeContainer list:\n"+Arrays.toString(empArr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Arrays.sort可以直接给基本数据类型(原生数据类型)排序
    // 因为内部定义了 sort(int[] a), sort(long[] a)等方法.
    @Test
    public void testArraysSortForInt() {
        int[] intArr = {5,9,1,10};
        System.out.println("基本数据类型int数组排序前: " + Arrays.toString(intArr));
        Arrays.sort(intArr);
        System.out.println("基本数据类型int数组排序后: " + Arrays.toString(intArr) + "\r\n");
    }

    // Arrays.sort可以给原生数据类型的包装类排序,例如Integer,原因Integer实现了Comparable接口
    @Test
    public void testArraysSortForInteger() {
        Integer[] intArr = {new Integer(100),new Integer(9), new Integer(20), new Integer(10)};
        System.out.println("排序前: " + Arrays.toString(intArr));
        Arrays.sort(intArr);
        System.out.println("排序后: " + Arrays.toString(intArr) + "\r\n");
    }

    // String实现了Comparable接口,此接口中有一个compareTo方法
    @Test
    public void testArraysSortForString() {
        String[] strArr = {"A", "C", "a", "e" ,"B", "Z", "E"};
        System.out.println("字符串数组排序前: " + Arrays.toString(strArr));
        Arrays.sort(strArr);
        System.out.println("字符串数组排序前: " + Arrays.toString(strArr) + "\r\n");
        // 自定义的对象要实现comparable接口才可以使用Arrays.sort排序
        NewEmployee[] newEmpArr = new NewEmployee[4];
        newEmpArr[0] = new NewEmployee(10, "Mikey", 25, 10000);
        newEmpArr[1] = new NewEmployee(20, "Arun", 29, 20000);
        newEmpArr[2] = new NewEmployee(5, "Lisa", 35, 5000);
        newEmpArr[3] = new NewEmployee(1, "Pankaj", 32, 50000);

        Arrays.sort(newEmpArr);
        System.out.println("Default Sorting of EmployeeContainer list:\n"+Arrays.toString(newEmpArr));
    }

    // 使用Collections.sort给List排序,集合中的元素必须要实现Comparable接口
    @Test
    public void useCollectionsSort() {
        List<String> strList = new ArrayList<>();
        strList.add("A");
        strList.add("C");
        strList.add("f");
        strList.add("B");
        strList.add("a");
        strList.add("Z");
        strList.add("E");
        System.out.println("字符串List排序前: " + strList);
        Collections.sort(strList);
        System.out.println("字符串List排序前: " + strList + "\r\n");
    }

    @Test
    public void testArraysSortUsingComparator() {
        //sorting custom object array
        FinalEmployee[] empArr = new FinalEmployee[4];
        empArr[0] = new FinalEmployee(10, "Mikey", 25, 10000);
        empArr[1] = new FinalEmployee(20, "Arun", 29, 20000);
        empArr[2] = new FinalEmployee(5, "Lisa", 35, 5000);
        empArr[3] = new FinalEmployee(1, "Pankaj", 32, 50000);

        // 使用给定的Comparator排序.
        Arrays.sort(empArr, FinalEmployee.SalaryComparator);
        System.out.println("EmployeeContainer list sorted by Salary:\n"+Arrays.toString(empArr));

        //sort employees array using Comparator by Age
        Arrays.sort(empArr, FinalEmployee.AgeComparator);
        System.out.println("EmployeeContainer list sorted by Age:\n"+Arrays.toString(empArr));

        //sort employees array using Comparator by Name
        Arrays.sort(empArr, FinalEmployee.NameComparator);
        System.out.println("EmployeeContainer list sorted by Name:\n"+Arrays.toString(empArr));
    }

    // 没有实现Comparable接口的测试类.
    private static class Employee {
        private int id;
        private String name;
        private int age;
        private long salary;
        public int getId() { return id; }
        public String getName() { return name; }
        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
        }
        public int getAge() { return age; }
        public long getSalary() { return salary; }
        public Employee(int id, String name, int age, int salary) { this.id = id;this.name = name;this.age = age;this.salary = salary; }
    }

    // 实现Comparable接口的测试类.
    private static class NewEmployee implements Comparable<NewEmployee> {

        private int id;
        private String name;
        private int age;
        private long salary;
        public int getId() { return id; }

        public String getName() { return name; }

        public int getAge() { return age; }

        public long getSalary() { return salary; }

        @Override
        public String toString() {
            return "NewEmployee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
        }

        public NewEmployee(int id, String name, int age, int salary) { this.id = id;this.name = name;this.age = age;this.salary = salary;}

        @Override
        public int compareTo(NewEmployee emp) {
            // 基于id升序排序
            // 返回负数，0，正数，代表小于，等于，大于emp对象
            return (this.id - emp.id);
        }

    }

    // 没有实现Comparable接口的测试类.但是定义了Comparator
    private static class FinalEmployee {

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
        public String toString() {
            return "FinalEmployee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", salary=" + salary +
                    '}';
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

}

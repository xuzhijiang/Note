package org.java.core.advanced.ComparableAndComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 使用Arrays.sort方法排序,给数组进行排序的示例

/**
 * 1. Arrays.sort可以直接给基本数据类型(原生数据类型)排序
 * 2. Arrays.sort可以给原生数据类型的包装类排序,例如Integer,原因Integer实现了Comparable接口
 * 3. 自定义的对象要实现comparable接口才可以使用Arrays.sort排序
 */
public class JavaObjectSorting {

    public static void main(String[] args) {

        useArraysSort();

        useCollectionsSort();
    }

    // 使用Collections.sort给List排序
    // 集合中的元素必须要实现Comparable接口
    private static void useCollectionsSort() {
        // 字符串List排序
        List<String> strList = new ArrayList<String>();
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

    private static void useArraysSort() {
        // 给原生的int数据类型排序
        int[] intArr = {5,9,1,10};
        System.out.println("基本数据类型int数组排序前: " + Arrays.toString(intArr));
        Arrays.sort(intArr);
        System.out.println("基本数据类型int数组排序后: " + Arrays.toString(intArr) + "\r\n");

        // String实现了Comparable接口,此接口中有一个compareTo方法
        String[] strArr = {"A", "C", "a", "e" ,"B", "Z", "E"};
        System.out.println("字符串数组排序前: " + Arrays.toString(strArr));
        Arrays.sort(strArr);
        System.out.println("字符串数组排序前: " + Arrays.toString(strArr) + "\r\n");

        Employee[] empArr = new Employee[4];
        empArr[0] = new Employee(10, "Mikey", 25, 10000);
        empArr[1] = new Employee(20, "Arun", 29, 20000);
        empArr[2] = new Employee(5, "Lisa", 35, 5000);
        empArr[3] = new Employee(1, "Pankaj", 32, 50000);

        try {
            // 当运行到这里的时候会抛出java.lang.ClassCastException:
            // org.java.core.advanced.ComparableAndComparator.Employee cannot be cast to java.lang.Comparable
            // 原因是Employee没有实现Comparable接口
            Arrays.sort(empArr);
            System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(empArr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        NewEmployee[] newEmpArr = new NewEmployee[4];
        newEmpArr[0] = new NewEmployee(10, "Mikey", 25, 10000);
        newEmpArr[1] = new NewEmployee(20, "Arun", 29, 20000);
        newEmpArr[2] = new NewEmployee(5, "Lisa", 35, 5000);
        newEmpArr[3] = new NewEmployee(1, "Pankaj", 32, 50000);

        Arrays.sort(newEmpArr);
        System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(newEmpArr));
    }
}
package org.java.core.advanced.ComparableAndComparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JavaObjectSorting {

    /**
     * This class shows how to sort primitive arrays(基本类型数组), 
     * Wrapper classes Object Arrays（包装类型对象数组）
     * @param args
     */
    public static void main(String[] args) {
        //sort primitives array like int array
    	//Integer实现了Comparable接口(也就是int的wrapper类实现了Comparable接口)
        int[] intArr = {5,9,1,10};
        Arrays.sort(intArr);
        System.out.println(Arrays.toString(intArr));
        
        //sorting String array
        // Notice: String实现了Comparable接口
        String[] strArr = {"A", "C", "B", "Z", "E"};
        Arrays.sort(strArr);
        System.out.println(Arrays.toString(strArr));
        
        //sorting list of objects of Wrapper classes
        List<String> strList = new ArrayList<String>();
        strList.add("A");
        strList.add("C");
        strList.add("B");
        strList.add("Z");
        strList.add("E");
        Collections.sort(strList);
        for(String str: strList) System.out.print(" "+str);
        
        Employee[] empArr = new Employee[4];
        empArr[0] = new Employee(10, "Mikey", 25, 10000);
        empArr[1] = new Employee(20, "Arun", 29, 20000);
        empArr[2] = new Employee(5, "Lisa", 35, 5000);
        empArr[3] = new Employee(1, "Pankaj", 32, 50000);
        
        //sorting employees array using Comparable interface implementation
        try {
        	 Arrays.sort(empArr);
        	 System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(empArr));
        	 // When I tried to run this, it throws following runtime exception.
             //java.lang.ClassCastException: 
             //org.java.core.advanced.ComparableAndComparator.Employee 
             //cannot be cast to java.lang.Comparable
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        NewEmployee[] newEmpArr = new NewEmployee[4];
        newEmpArr[0] = new NewEmployee(10, "Mikey", 25, 10000);
        newEmpArr[1] = new NewEmployee(20, "Arun", 29, 20000);
        newEmpArr[2] = new NewEmployee(5, "Lisa", 35, 5000);
        newEmpArr[3] = new NewEmployee(1, "Pankaj", 32, 50000);
        
        Arrays.sort(newEmpArr);
        //如您所见，Employees数组按id按升序排序, ascending order.
        System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(newEmpArr));
    }
}
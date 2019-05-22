package org.java.core.advanced.ComparableAndComparator;

import java.util.Arrays;

public class JavaObjectSortFinal {
	
	/**
    * This class shows how to sort custom objects array/list
    * implementing Comparable and Comparator interfaces
    * @param args
    */
   public static void main(String[] args) {
       //sorting custom object array
	   FinalEmployee[] empArr = new FinalEmployee[4];
       empArr[0] = new FinalEmployee(10, "Mikey", 25, 10000);
       empArr[1] = new FinalEmployee(20, "Arun", 29, 20000);
       empArr[2] = new FinalEmployee(5, "Lisa", 35, 5000);
       empArr[3] = new FinalEmployee(1, "Pankaj", 32, 50000);
       
       //sorting employees array using Comparable interface implementation
       Arrays.sort(empArr);
       System.out.println("Default Sorting of Employees list:\n"+Arrays.toString(empArr));
       
       //sort employees array using Comparator by Salary
       Arrays.sort(empArr, FinalEmployee.SalaryComparator);
       System.out.println("Employees list sorted by Salary:\n"+Arrays.toString(empArr));
       
       //sort employees array using Comparator by Age
       Arrays.sort(empArr, FinalEmployee.AgeComparator);
       System.out.println("Employees list sorted by Age:\n"+Arrays.toString(empArr));
       
       //sort employees array using Comparator by Name
       Arrays.sort(empArr, FinalEmployee.NameComparator);
       System.out.println("Employees list sorted by Name:\n"+Arrays.toString(empArr));
       
       //Employees list sorted by ID and then name using Comparator class
       empArr[0] = new FinalEmployee(1, "Mikey", 25, 10000);
       Arrays.sort(empArr, new EmployeeComparatorByIdAndName());
       System.out.println("Employees list sorted by ID and Name:\n"+Arrays.toString(empArr));
   }
	
}

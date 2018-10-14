package org.java.core.advanced.ComparableAndComparator;

import java.util.Comparator;

/**
 * here is the separate class implementation of Comparator
 * interface 
 */
public class EmployeeComparatorByIdAndName implements Comparator<FinalEmployee> {

    @Override
    public int compare(FinalEmployee o1, FinalEmployee o2) {
        int flag = o1.getId() - o2.getId();
        if(flag==0) flag = o1.getName().compareTo(o2.getName());
        return flag;
    }

}

package org.java.core.base.collection.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * List的遍历
 */
public class ListTraversalDemo {
    public static void main(String[] args) {
        ArrayListTraversal();

        LinkedListTraversal();
    }

    private static void ArrayListTraversal() {
        System.out.println("********************ArrayList**********************");
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        System.out.println("**************普通for循环,通过下标遍历**********************");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        System.out.println("**************通过迭代器遍历**********************");
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.println("**************通过增强for来遍历**********************");
        for(Integer number : list){
            System.out.print(number + " ");
        }
    }

    private static void LinkedListTraversal() {
        System.out.println();
        System.out.println();
        System.out.println("********************LinkedList**********************");
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        System.out.println("**************普通for循环,通过下标遍历**********************");
        for(int i = 0; i < list.size(); i++){
            System.out.print(list.get(i) + " ");
        }
        System.out.println();

        System.out.println("**************通过迭代器遍历**********************");
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            System.out.print(it.next() + " ");
        }
        System.out.println();

        System.out.println("**************通过增强for来遍历**********************");
        for(Integer number : list){
            System.out.print(number + " ");
        }
    }
}

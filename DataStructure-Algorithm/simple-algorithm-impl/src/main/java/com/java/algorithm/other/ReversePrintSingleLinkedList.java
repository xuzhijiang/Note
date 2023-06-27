//package com.java.algorithm.other;
//
//import java.util.Iterator;
//import java.util.Stack;
//
///**
// * 反向打印单向链表
// */
//public class ReversePrintSingleLinkedList {
//
//    public static void main(String[] args) {
//        SingleLinkList<Integer> singleLinkList = new SingleLinkList<>();
//        for (int i = 0; i < 10; i++) {
//            singleLinkList.addFirst(i);
//        }
//        System.out.println("************原单向链表***********");
//        singleLinkList.display();
//        System.out.println();
//        System.out.println();
//
//        System.out.println("***********反向打印之后***********");
//        // 借助Stack来反向打印单向链表
//        reversePrintUsingStack(singleLinkList);
//    }
//
//    /**
//     * 利用栈的先进后出特性
//     * @param singleLinkList
//     */
//    public static void reversePrintUsingStack(SingleLinkList<Integer> singleLinkList) {
//        Stack<Integer> stack = new Stack<>();
//        Iterator iterator = singleLinkList.iterator();
//        while (iterator.hasNext()) {
//            stack.push((Integer) iterator.next());
//        }
//
//        while (!stack.isEmpty()) {
//            System.out.print(stack.pop() + "\t");
//        }
//    }
//
//
//}

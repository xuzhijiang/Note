package com.java.datastructure.collection.list.linkedlist;

import org.junit.Test;

import java.util.Iterator;

public class MyLinkedListTest{

    @Test
    public void testAddFirst() {
        SingleLinkList<Integer> linkList = new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        linkList.display();
    }

    @Test
    public void testRemove() {
        SingleLinkList<Integer> linkList = new SingleLinkList<>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        if (!linkList.isEmpty()) {
            linkList.remove(5);
        }
        linkList.display();
    }

    @Test
    public void testRemoveFisrt() {
        SingleLinkList<Integer> linkList = new SingleLinkList<>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        linkList.removeFirst();
        linkList.display();
    }

    @Test
    public void testIterator() {
        SingleLinkList<Integer> linkList = new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        System.out.println("链表中元素：");
        linkList.display();
        System.out.println("\n开始迭代：");
        // 特别的，由于我们的迭代器实现了Java的标准接口(java.lang.Iterable)，
        // 所以我们可以使用java的增强for循环来进行迭代，
        // 如果没有实现Iterable接口，而是实现了我们自己模仿JDK的Iterable定义的MyIterable，是无法使用增强for循环的
        Iterator<Integer> iterator = linkList.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println(next);
        }
    }

    @Test
    public void testDoubleAddFirst() {
        DoubleLinkList<Integer> linkList=new DoubleLinkList<Integer>();
        for (int i = 0; i < 5; i++) {
            linkList.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            linkList.addLast(i);
        }
        linkList.display();
    }
}

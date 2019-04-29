package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.Deque;

/**
 * Java LinkedList Deque����
 * ��������ǽ�̽��LinkedList���������ΪDeque������ 
 * ����ʹ����Щ������ʵ�ֶ��л��ջ��
 * ���ǽ��ں�������������Stack��Queues������빤����
 */
public class LinkedListDequeOperationsDemo {
	public static void main(String[] args) {
		Deque<Integer> names = new LinkedList<Integer>();
		names.add(2);
		names.addFirst(1);
		names.addLast(3);
		names.addFirst(0);
		names.addLast(4);
		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
		
		names.removeFirst();
		names.removeLast();
		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
	}
}

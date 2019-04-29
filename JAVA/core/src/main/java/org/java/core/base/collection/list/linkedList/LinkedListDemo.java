package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * Java LinkedList֧���칹Ԫ�ء� ���ǣ����鲻Ҫʹ��û�з��͵ļ��ϡ�
 */
public class LinkedListDemo {
	public static void main(String[] args) {
		List names = new LinkedList();
		names.add("Rams");
		names.add("Posa");
		names.add("Chinni");
		names.add(2011);

		System.out.println("LinkedList content: " + names);
		System.out.println("LinkedList size: " + names.size());
	}
}

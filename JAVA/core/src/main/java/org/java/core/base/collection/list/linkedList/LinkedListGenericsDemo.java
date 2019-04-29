package org.java.core.base.collection.list.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * ������֪��Java Generics���ڱ�дType Safety��̺����ã�
 * �����ڱ���ʱ����Stronger���ͼ�顣 ���Ƕ�������(casting overhead)����ת������Ҳ�����á�
 * They are also useful to eliminate the casting overhead.
 */
public class LinkedListGenericsDemo
{
  public static void main(String[] args) 
  {
	List<String> names = new LinkedList<>();
	names.add("Rams");
	names.add("Posa");
	names.add("Chinni");
    // We cannot add other than Strings, �����׳�����ʱ����
    // names.add(2011);
			
	System.out.println("LinkedList content: " + names);
	System.out.println("LinkedList size: " + names.size());
  }
}

package org.java.core.base.collection.linkedList;

import java.util.LinkedList;
import java.util.List;

/**
 * 众所周知，Java Generics对于编写Type Safety编程很有用，
 * 并且在编译时进行Stronger类型检查。 它们对于消除(casting overhead)类型转换开销也很有用。
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
    // We cannot add other than Strings, 它会抛出编译时错误。
    // names.add(2011);
			
	System.out.println("LinkedList content: " + names);
	System.out.println("LinkedList size: " + names.size());
  }
}

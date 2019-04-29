package org.java.core.base.collection.queue;

import java.util.*;

// 队列peek（）操作
// peek（）操作用于从队列的头部检索元素，而不删除它。 
// 如果它成功执行检查操作，则返回队列的head元素。 否则返回null值。
public class QueuePeekOperation {
   public static void main(String[] args) {
		
	Queue<String> queue = new LinkedList<>();
	queue.add("one");
		
	System.out.println(queue.peek());
	System.out.println(queue);
	queue.clear();
	System.out.println(queue.peek());
	// 如果我们尝试在空Queue上调用peek（）方法，它将返回null值，但不会抛出异常
   }
}

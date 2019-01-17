package org.java.core.base.collection.queue;

import java.util.*;

// Java Queue Examine OperationsJava队列检查操作

//  如果此操作成功执行，则返回队列的head元素而不删除它。 
//众所周知，Queue支持两种形式的检查操作：

//Queue.element（）：
//如果操作失败，则抛出异常。
//
//Queue.peek（）：
//如果操作失败，则返回特殊值。
//
//注意： - 这里特殊值可能是“假”或“空”
// Note: Here special value may be either "false" or "null"

//Queue element() operation
//element（）操作用于从队列头部检索元素，而不删除它。 如果它成功执行检查操作，
//则返回队列的head元素。 否则抛出java.util.NoSuchElementException。


public class QueueElementOperation {
   public static void main(String[] args) {
		
	Queue<String> queue = new LinkedList<>();
	queue.add("one");
		
	System.out.println(queue.element());
	System.out.println(queue);
	queue.clear();
	System.out.println(queue.element());
	// 如果我们尝试在空Queue上调用element（）方法，它会抛出异常
   }
}

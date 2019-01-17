package org.java.core.base.collection.queue;

import java.util.*;

// Java Queue Delete Operations
// 如果成功执行，Delete操作将返回队列的head元素

// 我们知道，Queue支持两种形式的删除操作：

// Queue.remove（）：如果操作失败，则抛出异常。
// Queue.poll（）：如果操作失败，则返回特殊值。

// 注意： - 这里特殊值可能是“假”或“空”
// NOTE:- Here special value may be either “false” or “null”

// Queue remove() operation
// remove（）操作用于从队列头部删除元素。 如果它成功执行删除操作，则返回队列的head元素。 
// 否则抛出java.util.NoSuchElementException。
public class QueueRemoveOperation 
{
   public static void main(String[] args) 
   {		
	// 由于我们的队列只有两个元素，当我们尝试第三次调用remove（）方法时，它会抛出异常
	Queue<String> queue = new LinkedList<>();
	queue.offer("one");
	queue.offer("two");		
	System.out.println(queue);		
	System.out.println(queue.remove());
	System.out.println(queue.remove());		
	System.out.println(queue.remove());		
   }
}

// 注意：-
// Queue.remove（element）用于从队列中删除指定的元素。 如果它成功执行删除操作，
// 则返回“true”值。 否则返回“false”值。

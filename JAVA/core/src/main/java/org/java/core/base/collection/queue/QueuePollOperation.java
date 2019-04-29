package org.java.core.base.collection.queue;

import java.util.*;

// Queue poll() operation(轮询，投票)

// poll（）操作用于从队列头部删除元素。 如果它成功执行删除操作，则返回队列的head元素。 否则返回“null”值。
// 
public class QueuePollOperation 
{
   public static void main(String[] args) 
   {		
	Queue<String> queue = new LinkedList<>();
	queue.offer("one");
	queue.offer("two");		
	System.out.println(queue);		
	System.out.println(queue.poll());
	System.out.println(queue.poll());		
	System.out.println(queue.poll());		
	// 由于我们的队列只有两个元素，当我们尝试第三次调用poll（）方法时，它返回null值，
   }
}

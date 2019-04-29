package org.java.core.base.collection.queue;

import java.util.concurrent.*;

// Java Queue Insert Operations
// Queue Insert操作成功执行，则返回“true”值。

// Queue支持两种形式的插入操作：
// Queue.add（E）：如果操作失败，则抛出异常。
// Queue.offer（E）：如果操作失败，则返回特殊值。

// 注意： - 这里特殊值可能是“假”或“空”
// NOTE: Here special value may be either false or null

// Queue add() operation
// add（）操作用于将新元素插入队列。 如果它成功执行插入操作，则返回“true”值。 
// 否则抛出java.lang.IllegalStateException。
public class QueueAddOperation {
   public static void main(String[] args) {
	
	// 由于我们的队列仅限于两个元素，当我们尝试使用BlockingQueue.add（）添加第三个元素时，它会抛出异常
	BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

	System.out.println(queue.add("one"));
	System.out.println(queue.add("two"));
	System.out.println(queue);
	System.out.println(queue.add("three"));
	System.out.println(queue);
   }
}

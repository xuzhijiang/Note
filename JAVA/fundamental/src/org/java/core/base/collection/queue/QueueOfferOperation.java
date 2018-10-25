package org.java.core.base.collection.queue;

import java.util.concurrent.*;

// Queue offer() operation
// offer（）操作用于将新元素插入队列。 如果它成功执行插入操作，则返回“true”值。 否则返回“false”值。
public class QueueOfferOperation {
	public static void main(String[] args) {
		// 由于我们的队列仅限于两个元素，当我们尝试使用BlockingQueue.offer（）操作添加第三个元素时，它返回“false”值
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

		System.out.println(queue.offer("one"));
		System.out.println(queue.offer("two"));
		System.out.println(queue);
		System.out.println(queue.offer("three"));
		System.out.println(queue);
	}
}

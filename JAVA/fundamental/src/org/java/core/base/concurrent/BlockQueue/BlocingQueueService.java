package org.java.core.base.concurrent.BlockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Java {@link java.util.concurrent.BlockingQueue} doesn¡¯t accept null values
 * and throw <code>NullPointerException</code> if you try to store null value in
 * the queue. Java BlockingQueue implementations are thread-safe. All queuing
 * methods are atomic in nature and use internal locks or other forms of
 * concurrency control.
 * <p>
 * it¡¯s primarily used for implementing producer consumer problem. We don¡¯t need
 * to worry about waiting for the space to be available for producer or object
 * to be available for consumer in BlockingQueue because it¡¯s handled by
 * implementation classes of BlockingQueue.
 * <p>
 * Java provides several BlockingQueue implementations such as
 * ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue,
 * SynchronousQueue etc.
 * <p>
 * While implementing producer consumer problem in BlockingQueue, we will use
 * ArrayBlockingQueue implementation.
 * <p>
 * <strong>put(E e):</strong> This method is used to insert elements to the
 * queue. If the queue is full, it waits for the space to be available.
 * <p>
 * <strong>E take():</strong> This method retrieves and remove the element from
 * the head of the queue. If queue is empty it waits for the element to be
 * available.
 */
public class BlocingQueueService {

	public static void main(String[] args) {
		BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(10);

		Producer p = new Producer(queue);
		Consumer c = new Consumer(queue);

		p.start();
		c.start();
	}
}

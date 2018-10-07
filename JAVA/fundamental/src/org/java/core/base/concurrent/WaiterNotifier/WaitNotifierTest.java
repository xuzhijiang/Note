package org.java.core.base.concurrent.WaiterNotifier;

/**
 * These methods can be used to implement producer consumer 
 * problem where consumer threads are waiting for the 
 * objects in Queue and producer threads put object in queue 
 * and notify the waiting threads.
 * 这些方法可用于实现生产者消费者问题，其中消费者线程正在等待队列中的对象，
 * 生产者线程将对象放入队列并通知等待的线程。
 */
public class WaitNotifierTest {
	public static void main(String[] args) {
        Message msg = new Message("process it");
        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"waiter").start();
        
        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "waiter1").start();
        
        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        System.out.println("All the threads are started");
    }
}

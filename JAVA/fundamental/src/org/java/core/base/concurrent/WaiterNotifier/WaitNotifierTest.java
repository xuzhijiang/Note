package org.java.core.base.concurrent.WaiterNotifier;

/**
 * These methods can be used to implement producer consumer 
 * problem where consumer threads are waiting for the 
 * objects in Queue and producer threads put object in queue 
 * and notify the waiting threads.
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

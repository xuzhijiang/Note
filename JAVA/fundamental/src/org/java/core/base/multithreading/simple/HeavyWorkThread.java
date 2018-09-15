package org.java.core.base.multithreading.simple;

/**
 * Thread can be called lightweight process. 
 * Thread requires less resources to create 
 * and exists in the process, thread shares 
 * the process resources.
 * <p>
 * Every java application has at least one 
 * thread ¨C main thread. But from application
 * point of view ¨C main is the first java thread
 * and we can create multiple threads from it.
 * <p>
 * Multithreading refers to two or more threads executing 
 * concurrently in a single program. A computer single core 
 * processor can execute only one thread at a time and time 
 * slicing is the OS feature to share processor time between 
 * different processes and threads.
 * <p>
 * Java Thread Benefits Java Threads are lightweight compared 
 * to processes, it takes less time and resource to create a thread.
 * Threads share their parent process data and code
 * Context switching between threads is usually less expensive 
 * than between processes.Thread intercommunication is relatively easy than process communication.
 * <p>
 * Once we start any thread, it¡¯s execution depends on the OS 
 * implementation of time slicing and we can¡¯t control their 
 * execution. However we can set threads priority but even then 
 * it doesn¡¯t guarantee that higher priority thread will be executed first.
 * <p>
 * Runnable vs Thread
 * If your class provides more functionality rather than just running as Thread,
 * you should implement Runnable interface to provide a way to run it as Thread. 
 * If your class only goal is to run as Thread, you can extend Thread class.
 * <p>
 * Implementing Runnable is preferred because java supports implementing 
 * multiple interfaces. If you extend Thread class, you can¡¯t extend any other classes.
 * <p>
 * Tip: As you have noticed that thread doesn¡¯t return any value but what if
 * we want our thread to do some processing and then return the result to our 
 * client program, check our Java Callable Future.
 */
public class HeavyWorkThread extends Thread{
	
	@Override
	public void run() {
		System.out.println("Doing heavy processing - START -" + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
			//Get database connection, delete unused data from DB
			doDBProcessing();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Doing heavy processing - END -" + Thread.currentThread().getName());
	}

	private void doDBProcessing() throws InterruptedException{
		Thread.sleep(5000);
	}
}

package org.java.core.base.concurrent.WaiterNotifier;

/**
 * <strong>notify</strong><br>
 * notify method wakes up only one thread waiting on 
 * the object and that thread starts execution. So if 
 * there are multiple threads waiting for an object, 
 * this method will wake up only one of them. The choice
 * of the thread to wake depends on the OS implementation 
 * of thread management
 * <strong>notifyAll</strong>
 * notifyAll method wakes up all the threads waiting on the object,
 * although which one will process first depends on the OS implementation.
 * <p>
 * A class that will process on Message object and then
 * invoke notify method to wake up threads waiting for 
 * Message object. Notice that synchronized block is used 
 * to own the monitor of Message object.
 * synchronized块用于使当前线程拥有Message对象的监视器。如果不用
 * synchronized block，将会抛异常: 
 * {@link java.lang.IllegalMonitorStateException} - if the current thread is not the owner of this object's monitor.
 */
public class Notifier implements Runnable{
	
	private Message msg;
	
	public Notifier(Message msg) {
		this.msg = msg;
	}
	
	@Override
	public void run() {
		String name = Thread.currentThread().getName();
        System.out.println(name+" started");
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMsg(name+" Notifier work done");
                msg.notify();
                //msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

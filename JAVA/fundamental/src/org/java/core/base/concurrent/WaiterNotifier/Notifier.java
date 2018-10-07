package org.java.core.base.concurrent.WaiterNotifier;

/**
 * <strong>notify</strong><br>
 * notify method wakes up only one thread waiting on 
 * the object and that thread starts execution. So if 
 * there are multiple threads waiting for an object, 
 * this method will wake up only one of them. The choice
 * of the thread to wake depends on the OS implementation 
 * of thread management
 * notify方法只唤醒一个等待该对象的线程，该线程开始执行。 
 * 因此，如果有多个线程在等待对象，则此方法将仅唤醒其中一个。 要唤醒的线程的选择
 * 取决于OS线程管理的实现。
 * <strong>notifyAll</strong>
 * notifyAll method wakes up all the threads waiting on the object,
 * although which one will process first depends on the OS implementation.
 * notifyAll方法唤醒等待该对象的所有线程，尽管哪个线程将首先被唤醒取决于OS实现。
 * <p>
 * A class that will process on Message object and then
 * invoke notify method to wake up threads waiting for 
 * Message object. Notice that synchronized block is used 
 * to own the monitor of Message object.
 * 将在Message对象上处理然后调用notify方法以唤醒等待Message对象的线程的类。
 * 请注意，synchronized块用于使当前线程拥有Message对象的监视器。如果不用
 * synchronized block，将会抛异常: 
 * {@link java.lang.IllegalMonitorStateException} - 
 * if the current thread is not the owner of this object's monitor.
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
                //有两个线程在Message对象上等待，而notify（）
                //方法只唤醒其中一个，另一个线程仍在等待获得通知。
//              msg.notify();
                
                msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
	}
}

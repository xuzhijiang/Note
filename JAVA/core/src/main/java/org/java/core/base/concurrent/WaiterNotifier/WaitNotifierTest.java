package org.java.core.base.concurrent.WaiterNotifier;

/**
 * 在"任何对象"上调用这些方法(wait,notify等)的线程应该具有"对象监视器"
 * (也就是线程必须要拥有锁对象,才能调用锁对象的这些方法)，
 * 否则它会抛出java.lang.IllegalMonitorStateException异常。
 *
 * - wait() 、notify()、notifyAll() 调用的前提都是获得了对象的锁(也可称为对象监视器)
 * - 调用 wait() 方法后线程会释放锁，进入 `WAITING` 状态，该线程也会被移动到"等待队列"中
 * - 调用 notify() 方法会将"等待队列"中的线程移动到"同步队列"中，线程状态也会更新为 `BLOCKED`
 * - 从 wait() 方法返回的前提是调用 notify() 方法的线程释放锁，wait() 方法的线程获得锁.
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


/**
 * wait方法有3中变体,一个方法是无限期地等待任何其他线程调用此对象上的
 * notify或notifyAll方法来唤醒当前线程, 其他两个变体是使当前线程在
 * 唤醒之前等待特定的时间
 *
 * 将等待其他线程调用notify以完成其处理的类。 请注意，
 * Waiter线程使用synchronized块在Message对象上拥有监视器。
 */
class Waiter implements Runnable{
        private Message msg;

        public Waiter(Message m){
                this.msg=m;
        }

        @Override
        public void run() {
                String name = Thread.currentThread().getName();
                // 注意锁也可以使用Waiter.class
                // Waiter.class对象 是 java.lang.Class类 的实例，
                // java.lang.Class类也是java.lang.Object类的子类
                // 所以Waiter.class也是可以调用Waiter.class.wait()方法的,
                // 调用的就是Waiter.class对象实例的wait()方法.
                synchronized (msg) {
                        try{
                                System.out.println(name+" - waiting to get notified at time:"+System.currentTimeMillis());
                                // 释放了锁并进入 `WAITING` 状态
                                msg.wait();
                        }catch(InterruptedException e){
                                e.printStackTrace();
                        }
                        System.out.println(name+" thread got notified at time:"+System.currentTimeMillis());
                        // process the message now
                        System.out.println(name+" processed: "+msg.getMsg());
                }
        }
}

/**
 * notify方法只唤醒一个等待该对象的线程，该线程开始执行。
 * 因此，如果有多个线程在等待对象，则此方法将仅唤醒其中一个。 要唤醒的线程的选择
 * 取决于OS线程管理的实现。
 *
 * notifyAll方法唤醒等待该对象的所有线程，尽管哪个线程将首先被唤醒取决于OS实现。
 *
 * 将在Message对象上处理然后调用notify方法以唤醒等待Message对象的线程的类。
 * 请注意，synchronized块用于使当前线程拥有Message对象的监视器。如果不用
 * synchronized block，将会抛异常:
 * {@link java.lang.IllegalMonitorStateException} -
 * if the current thread is not the owner of this object's monitor.
 */
class Notifier implements Runnable{

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
                                msg.notifyAll(); // 利用msg对象完成线程通信.
                        }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
        }
}

class Message {
        private String msg;

        public Message(String msg) {
                this.msg = msg;
        }

        public String getMsg() {
                return this.msg;
        }

        public void setMsg(String msg) {
                this.msg = msg;
        }
}
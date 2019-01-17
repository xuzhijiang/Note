package org.java.core.base.concurrent.WaiterNotifier;

/**
 * Object wait methods has three variance, one which 
 * waits indefinitely for any other thread to call notify 
 * or notifyAll method on the object to wake up the current 
 * thread. Other two variances puts the current thread in wait 
 * for specific amount of time before they wake up.
 * wait方法有3中变体,一个方法是无限期地等待任何其他线程调用此对象上的
 * notify或notifyAll方法来唤醒当前线程, 其他两个变体是使当前线程在
 * 唤醒之前等待特定的时间。
 * <p>
 * A class that will wait for other threads to invoke 
 * notify methods to complete it’s processing. Notice 
 * that Waiter thread is owning monitor on Message object 
 * using synchronized block.
 * 将等待其他线程调用notify以完成其处理的类。 请注意，
 * Waiter线程使用synchronized块在Message对象上拥有监视器。
 */
public class Waiter implements Runnable{
    private Message msg;
    
    public Waiter(Message m){
        this.msg=m;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        synchronized (msg) {
            try{
                System.out.println(name+" - waiting to get notified at time:"+System.currentTimeMillis());
                msg.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(name+" thread got notified at time:"+System.currentTimeMillis());
            //process the message now
            System.out.println(name+" processed: "+msg.getMsg());
        }
    }
}

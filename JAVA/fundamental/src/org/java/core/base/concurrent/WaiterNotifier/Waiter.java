package org.java.core.base.concurrent.WaiterNotifier;

/**
 * Object wait methods has three variance, one which 
 * waits indefinitely for any other thread to call notify 
 * or notifyAll method on the object to wake up the current 
 * thread. Other two variances puts the current thread in wait 
 * for specific amount of time before they wake up.
 * <p>
 * A class that will wait for other threads to invoke 
 * notify methods to complete it¡¯s processing. Notice 
 * that Waiter thread is owning monitor on Message object 
 * using synchronized block.
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
            System.out.println(name+" waiter thread got notified at time:"+System.currentTimeMillis());
            //process the message now
            System.out.println(name+" processed: "+msg.getMsg());
        }
    }
}

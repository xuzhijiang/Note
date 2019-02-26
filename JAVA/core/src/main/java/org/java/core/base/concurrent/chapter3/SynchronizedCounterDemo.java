package org.java.core.base.concurrent.chapter3;

public class SynchronizedCounterDemo {

    public static void main(String[] args){
        // Java同步实例--对象锁
        //因此每次只允许一个线程调用该方法
//        Counter counter = new Counter();
//        Thread  thread1 = new CounterThread(counter);
//        Thread  thread2 = new CounterThread(counter);
//
//        thread1.start();
//        thread2.start();


        // 如果两个线程引用了两个不同的Counter实例，那么他们可以同时调用add()方法
        // 这些方法调用了不同的对象，因此这些方法也就同步在不同的对象上
        Counter counterA = new Counter();
        Counter counterB = new Counter();
        Thread  threadA = new CounterThread(counterA);
        Thread  threadB = new CounterThread(counterB);

        threadA.start();
        threadB.start();



    }

}



// 计数器
class Counter{
    long count = 0;
    static long countStatic = 0;

    /**
     * Java同步实例--对象锁
     * @param value
     */
    public synchronized void add(long value){
        this.count += value;
        System. out.println(Thread. currentThread().getName()+":"+ count);
    }

    /**
     * // 在有多个实例的情况下，可以将Counter的代码改一下--类锁。
     * @param value
     */
    public static synchronized  void addStatic( long value ){
        countStatic += value;
        System. out.println(Thread. currentThread().getName()+":"+ countStatic);
    }
}

//计数线程
class CounterThread extends Thread{

    protected Counter counter = null;

    public CounterThread(Counter counter){
        this.counter = counter;
    }

    public void run() {
        for(int i=0; i<10; i++){
            counter.add(1);
        }
    }
}
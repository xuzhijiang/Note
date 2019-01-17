package org.java.core.advanced;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子操作在单个任务单元中执行，不受其他操作的干扰 在多线程环境中必须进行原子操作以避免数据不一致。
 *
 */
public class JavaAtomic {
	
    public static void main(String[] args) throws InterruptedException {
    	ProcessingThread pt = new ProcessingThread();
    	Thread t1 = new Thread(pt, "thread1");
    	t1.start();
    	Thread t2 = new Thread(pt, "thread2");
    	t2.start();
    	t1.join();
    	t2.join();
    	System.out.println("Processing count=" + pt.getCount());
    }
}

//class ProcessingThread implements Runnable {
//    private int count;
//
//    @Override
//    public void run() {
//        for (int i = 1; i < 5; i++) {
//            processSomething(i);
//            count++;
//        }
//    }
//
//    public int getCount() {
//        return this.count;
//    }
//
//    private void processSomething(int i) {
//        // processing some job
//        try {
//            Thread.sleep(i * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}

//如果您将运行上述程序，您会注意到计数值在5,6,7,8之间变化。
//原因是因为count++不是原子操作。 例如count=4，当一个线程读取它的值并将其递增1时，但是这个时候，count在当前
//线程中并没有完成count+1之后将新值5赋值给count的操作，导致其他线程读取的count还是旧的count(4)，
//从而导致错误的结果。

class ProcessingThread implements Runnable {
    private AtomicInteger count = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 1; i < 5; i++) {
            processSomething(i);
            count.incrementAndGet();
        }
    }

    public int getCount() {
        return this.count.get();
    }

    private void processSomething(int i) {
        // processing some job
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

//使用并发类进行原子操作的好处是我们不需要担心同步。
//这提高了代码的可读性，减少了错误的可能性。使用并发类进行原子操作比涉及锁定资源的同步更有效。

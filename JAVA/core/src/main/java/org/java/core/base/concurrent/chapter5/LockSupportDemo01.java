package org.java.core.base.concurrent.chapter5;

/**
 * LockSupport对中断的响应性:
 *
 * 最终线程会打印出thread over----true。这说明 线程如果因为调用park而阻塞的话，
 * 能够响应中断请求(中断状态被设置成true)，但是不会抛出InterruptedException 。
 */
public class LockSupportDemo01 {

    public static void main(String[] args) throws InterruptedException {
        t2();
    }

    private static void t2() throws InterruptedException {

        Thread t = new Thread(){
            private int count = 0;
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;

                while((end - start) <= 1000){
                    //System.out.println("-----count: " + count);
                    count++;
                    end = System.currentTimeMillis();
                }
                System.out.println("after 1 second, count = " + count);

                // 等待获取许可
                java.util.concurrent.locks.LockSupport.park();
                System.out.println("thread over---: " + Thread.currentThread().isInterrupted());
            }
        };

        t.start();
        System.out.println("1111");
        Thread.sleep(5000);

        // 中断线程
        t.interrupt();

       System.out.println("main over");
    }
}

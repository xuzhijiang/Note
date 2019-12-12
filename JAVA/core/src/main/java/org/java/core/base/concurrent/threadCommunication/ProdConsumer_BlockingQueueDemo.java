package org.java.core.base.concurrent.threadCommunication;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * V3.0版的生产消费模式(不再需要手动加锁解锁了)
 *
 * volatile/CAS/AtomicInteger/BlockQueue/线程交互/原子引用
 */
public class ProdConsumer_BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Comsumer").start();

        // 暂停一会儿线程
        TimeUnit.SECONDS.sleep(5L);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("5秒时间到,大boss主线程叫停,活动结束");

        myResource.stop();
    }

    /**
     * 共享资源类
     */
    private static class MyResource {

        private volatile boolean FLAG = true; // 默认开启,进行生产+消费
        private AtomicInteger atomicInteger = new AtomicInteger();
        BlockingQueue<String> blockingQueue = null;

        public MyResource(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
            // 打印传入的BlockingQueue的具体实现
            System.out.println(blockingQueue.getClass().getName());
        }

        public void myProd() throws Exception{
            String data = null;
            boolean retValue = false;
            while (FLAG) {
                data = String.valueOf(atomicInteger.incrementAndGet());
                retValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                if (retValue) {
                    System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
                }
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println(Thread.currentThread().getName() + "\t 大老板叫停了,表示FLAG=false,生产动作结束");
        }

        public void myConsumer() throws Exception{
            String result = null;
            while (true) {
                result = blockingQueue.poll(2L, TimeUnit.SECONDS);
                if (null == result) {
                    FLAG = false;
                    System.out.println(Thread.currentThread().getName() + "\t 超过2秒中没有取到蛋糕,消费退出");
                    System.out.println();
                    System.out.println();
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t 消费队列蛋糕" + result + "成功");
            }
        }

        public void stop() {
            this.FLAG = false;
        }
    }
}

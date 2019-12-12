package org.java.core.base.collection.queue.BlockingQueue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    /**
     * 抛出异常组的阻塞队列
     */
    @Test
    public void test01() {
        // ArrayBlockingQueue为什么是有界的?
        // Array在初始化的时候必须分配大小,否则会报错.(有例外: ArrayList也是用Array实现,默认是10,所以不用添初始值.)
        // List list = new ArrayList();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        // java.lang.IllegalStateException: Queue full
        // System.out.println(blockingQueue.add("d"));

        // @throws NoSuchElementException if this queue is empty
        System.out.println(blockingQueue.element());

        // 按顺序移除
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        // java.util.NoSuchElementException
        // System.out.println(blockingQueue.remove());

        // 定点移除
        // blockingQueue.remove("c");
    }

    /**
     * 不抛异常,返回boolean的api
     */
    @Test
    public void test02() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    /**
     * 阻塞相关的api
     */
    @Test
    public void test03() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");

        // 已经满了,会阻塞等待,消息中间件可能会用到这种场景,消息堆压,防止消息丢失
        //blockingQueue.put("d");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        // 因为已经没有元素了,所以会阻塞等待
        System.out.println(blockingQueue.take());
    }

    /**
     * 阻塞,超时之后就返回
     */
    @Test
    public void test04() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("b", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("c", 2, TimeUnit.SECONDS));
        // 等待2秒后返回
        System.out.println(blockingQueue.offer("d", 2, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        // 等待2秒后返回null
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
    }

    /**
     * 测试SynchronousQueue
     */
    @Test
    public void testSynchronousQueue() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        Thread t1 = new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put a");
                blockingQueue.put("a");

                System.out.println(Thread.currentThread().getName() + "\t put b");
                blockingQueue.put("b");

                System.out.println(Thread.currentThread().getName() + "\t put c");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA");
        t1.start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t 读取" + blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t 读取" + blockingQueue.take());

                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "\t 读取" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

        Thread.currentThread().join(17 * 1000);
    }
}

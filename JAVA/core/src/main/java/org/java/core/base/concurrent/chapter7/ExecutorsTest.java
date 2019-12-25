package org.java.core.base.concurrent.chapter7;

import java.util.concurrent.*;

public class ExecutorsTest {

    public static void main(String[] args) {
        createOwnThreadPool();

//        testFixed();
//        testSingle();
//        testCached();
    }

    public static void createOwnThreadPool() {
        ExecutorService threadPool = new ThreadPoolExecutor(2,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
                new ThreadPoolExecutor.CallerRunsPolicy()); // 该策略不会丢弃任务,也不会抛异常,而是把线程池做不了的任务交给调用者线程,这里就是main线程
//                new ThreadPoolExecutor.DiscardOldestPolicy()); // 不抛异常,把队列中等待最久的任务丢弃(也就是队列头部的任务删除)
//                new ThreadPoolExecutor.DiscardPolicy()); // 把线程池做不了的任务直接丢弃,不抛异常.

        // 实际生产中,如何配置线程池的最大数量?你是怎么考虑的?
        System.out.println("CPU核数: " + Runtime.getRuntime().availableProcessors());

        try {
            // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求
            for (int i = 0; i < 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void testFixed() {
        // 注意左侧一般使用ExecutorService来接受,而不是使用Executor来接受
        // 右侧不是直接new线程池,而是使用Executors辅助工具类来帮我们new
        ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池固定数线程,一池5个线程
        //  new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        // 核心数和最大数为我们传入的

        try {
            // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求
            for (int i = 0; i < 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void testSingle() {
        // 注意左侧一般使用ExecutorService来接受,而不是使用Executor来接受
        // 右侧不是直接new线程池,而是使用Executors辅助工具类来帮我们new
        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 一池一个处理线程
        // new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())
        // 核心数为1,最大为1

        try {
            // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    public static void testCached() {
        // 注意左侧一般使用ExecutorService来接受,而不是使用Executor来接受
        // 右侧不是直接new线程池,而是使用Executors辅助工具类来帮我们new
        ExecutorService threadPool = Executors.newCachedThreadPool();// 缓存可扩容的线程池: 一池N个线程
        // newCachedThreadPool内部是怎么创建ThreadPoolExecutor的?
        // new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        // 核心数为0,最大数为Integer.MAX_VALUE,队列为SynchronousQueue,意思就是有活就干,没活就缩

        try {
            // 模拟10个用户来办理业务,每个用户就是一个来自外部的请求
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                // 暂停线程
                // 可以把这行取消注释,然后再看看有多少个线程
                // TimeUnit.MILLISECONDS.sleep(200L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}

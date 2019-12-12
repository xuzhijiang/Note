package org.java.core.base.concurrent.chapter7;

import org.junit.Test;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {

    /**
     * 用线程池来管理线程，可以使用以下方式来让主线程等待线程池中所有任务执行完毕
     */
    @Test
    public void awaitTerminationTest() throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 1, TimeUnit.SECONDS, queue);

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("running is over");
            }
        });

        threadPoolExecutor.execute(() -> {
            System.out.println("running2");
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("running2 is over");
        });

        // shutdown()会停止接受新任务，并且会平滑的关闭线程池中现有的任务
        threadPoolExecutor.shutdown();// 有序关闭，先前提交的任务将被执行，但不会接受任何新任务。

        // 尝试停止所有正在执行的任务，停止执行等待任务，
        // 并返回正在等待执行的任务列表。 这些任务被排空（删除）
        // 此方法不会等待正在执行任务终止。
        // threadPoolExecutor.shutdownNow();

        // 调用 `awaitTermination()` 方法的前提需要关闭线程池，如调用了 `shutdown()` 方法。
        // 如果不调用关闭线程池的方法，awaitTermination会永远返回false。
        // awaitTermination(1, TimeUnit.SECONDS)意思是每隔1秒去看下线程池中的线程是否都运行完成了，
        // 如果都运行完成了，就返回true，跳出while循环，然后主线程终止.
        while(!threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("thread is running.....");
        }

        System.out.println("main is over");
    }

}

package org.java.core.base.concurrent.chapter5.CyclicBarrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 假若有若干个线程都要进行写数据操作，并且只有所有线程都完成写数据操作之后，
 * 这些线程才能继续做后面的事情，此时就可以利用CyclicBarrier了.
 *
 * CyclicBarrier实际用途：假设我们要导入一批数据，因为数据量较大有4亿条，
 * 启动四个线程，每个线程负责导入1亿条数据，我们希望所有的数据导入完成之后，
 * 还在数据库中插入一条记录，表示任务执行完成
 */
public class CyclicBarrierDemo {

    // 集齐7棵龙珠才可以召唤神龙.
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("*************召唤神龙************");
        });

        for (int i = 1; i <= 7; i++) {
            final int tem = i;
            // sleep一会儿
            new Thread(() -> {
                try { TimeUnit.SECONDS.sleep(new Random().nextInt(2) + 1); } catch (InterruptedException e) { e.printStackTrace(); }

                System.out.println(Thread.currentThread().getName() + "\t 收集到第" + tem + "颗龙珠");
                // 等待 N 个线程都达到同步状态后继续运行的效果
                // 人到齐了才可以开会.早到的人就要等待.
                try { cyclicBarrier.await(); } catch (Exception e) { e.printStackTrace(); }
            }, String.valueOf(i)).start();
        }
    }

}

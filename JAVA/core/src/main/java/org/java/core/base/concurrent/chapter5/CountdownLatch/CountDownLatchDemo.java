package org.java.core.base.concurrent.chapter5.CountdownLatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 灭六国一统华夏
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException{
        // 初始值为6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        System.out.println("**********战争开始***************");
        for (int i = 1; i < 7; i++) {
            new Thread(() -> {
                try { TimeUnit.SECONDS.sleep(new Random().nextInt(6) + 1); } catch (InterruptedException e) { e.printStackTrace(); }

                System.out.println(Thread.currentThread().getName() + "国,被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach(i).getMsg()).start();
        }

        // 主线程就会等到CountDownLatch的值为0时才能继续往下执行
        // 调用await()方法的线程会被挂起
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "****************秦帝国一统天下************");
    }

}

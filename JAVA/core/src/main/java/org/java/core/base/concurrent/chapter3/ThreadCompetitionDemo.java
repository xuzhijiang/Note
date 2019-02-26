package org.java.core.base.concurrent.chapter3;

/**
 * 首先通过一个案例来演示多线程竞争共享资源会产生的问题。
 * 在以下代码中，我们使用两个线程，对于1个整形变量从各加五百万次。
 * 我们期望的结果是一千万，但结果是不是这样呢？
 *
 * 可以看到的结果并不是我们期望中的一千万。
 * 原因在于，count++并不是一个"原子操作"。每次自增实际上是分为3个步骤：
 *
 * 1. 获取count变量的当前值
 * 2. 将当前值加1
 * 3. 将加1后的值存储到count变量中
 *
 * 因为线程是并行执行的，因此这就可能出现问题。
 * 例如假设count变量当前值是0，主线程和自定义线程同时获取到这个值，
 * 主线程先完成自增的操作，将count变量的值设置为1。自定义线程随后完成自增的操作，
 * 因为自定义线程也是在0的基础上加1，然后将值赋值给count变量，
 * 最终导致实际上进行了两次自增操作，但实际上确只加了1。
 */
public class ThreadCompetitionDemo {

    // 在大部分教程中演示这个案例的时候整形变量都会采取long型，
    // 注意本文使用的int型，使用long变量是因为这个变量类型出现问题的可能性会更大.
    // 在后面的部分中，我们会进行详细讲解。
    static int count = 0;

    public  static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        new Thread() {
            @Override
            public void run() {
                // 变量的自增操作次数越多，出现问题的可能性越大。
                // 读者可以尝试将自增操作都改为加10次，会发现大部分情况下结果都是正确的。
                for(int i=0;i<5000000;i++){
                    count++;
                }
                System.out.println("自定义线程:计算完成......,耗时：" + (System.currentTimeMillis() - start));
            }
        }.start();

        for(int i=0;i<5000000;i++){
            count++;
        }

        System.out.println("主线程:计算完成....，耗时"+(System.currentTimeMillis()-start));

        // 休眠，是防止另外一个线程没执行完。就打印出了结果
        Thread.sleep(10000);
        System.out.println("count:"+count);
    }
}

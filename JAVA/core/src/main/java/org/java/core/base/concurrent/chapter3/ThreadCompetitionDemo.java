package org.java.core.base.concurrent.chapter3;

import org.junit.Test;

/**
 * 演示多线程竞争共享资源会产生的问题。
 *
 * 在以下代码中，我们使用两个线程，对于1个整形变量从各加五百万次。我们期望的结果是一千万，但结果并不是我们期望中的一千万。
 * 原因在于，count++并不是一个"原子操作"。每次自增实际上是分为3个步骤：
 *
 * 1. 获取count变量的当前值
 * 2. 将当前值加1
 * 3. 将加1后的值存储到count变量中
 *
 * 假如A线程在cpu上执行,取到count=10,然后还没有来及计算,这是cpu要切换到线程B来执行,这个时候要保存线程A的上下文,所以在
 * A的上下文中count=10,然后B执行,B拿到当前的count也是10,然后执行count++,count这个时候变成了11,然后cpu切换到线程A来执行,
 * 这时,count加完之后,也是11,等于2个线程一共加了2次,但是结果却只是加了一次.归根到底是由于count++并不是一个"原子操作".会被cpu中断.
 * 这也是所有多线程问题的根源.
 *
 */
public class ThreadCompetitionDemo {

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

        System.out.println("主线程:计算完成....，耗时"+(System.currentTimeMillis()-start) + "毫秒");

        // 休眠，是防止另外一个线程没执行完。就打印出了结果
        Thread.sleep(10000);
        System.out.println("count:"+count);
    }

    @Test
    public void solveThreadCompetition() throws InterruptedException {

        long start=System.currentTimeMillis();

        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i <5000000 ; i++) {
                    //我们可以通过synchronized关键字和锁来解决ThreadCompetitionDemo中出现的问题
                    synchronized (ThreadCompetitionDemo.class){
                        count++;
                    }
                }
                System.out.println("自定义线程:计算完成...，耗时"+(System.currentTimeMillis()-start));
            }
        }.start();
        for (int i = 0; i <5000000 ; i++) {
            synchronized (ThreadCompetitionDemo.class){
                count++;
            }
        }
        System.out.println("主线程:计算完成....，耗时"+(System.currentTimeMillis()-start));
        Thread.sleep(10000);
        System.out.println("count:"+count);

        // 里要注意的是，我们每次线程完成五百万次累加操作的时间，都在350毫秒左右，
        // 而ThreadCompetitionDemo大概只需要9毫秒左右，花费的时间接近40倍。
        // 这说明同步代码块虽然可以帮助我们将结果计算正确，但是在性能上却有非常大的影响。
    }

}

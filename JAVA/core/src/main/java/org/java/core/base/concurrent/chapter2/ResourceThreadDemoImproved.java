package org.java.core.base.concurrent.chapter2;

import org.junit.Test;

public class ResourceThreadDemoImproved {

    /**
     * 最低效的读取和处理A和B
     */
    @Test
    public void method1() throws InterruptedException {
        final long start=System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        // 我们使用Thread.currentThread().sleep(miliseconds)来模拟文件的读取和处理操作
        Thread.currentThread().sleep(5000);
        System.out.println("读取A文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理A文件");
        Thread.currentThread().sleep(2000);
        System.out.println("A文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");
        System.out.println("读取B文件开始...");
        Thread.currentThread().sleep(5000);
        System.out.println("读取B文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理B文件");
        Thread.currentThread().sleep(2000);
        System.out.println("B文件处理完成...，耗时："+(System.currentTimeMillis()-start)/1000+"秒");

        // 上面的模拟,其作用是让当前线程"休眠"，休眠的含义是在指定的时间范围内，
        // 线程不会再向CPU发送执行的请求(也就是不会再去抢占cpu)。等到休眠时间已过，
        // 才会重新请求CPU执行(重新抢占cpu)。

        // 需要注意的是，上面的代码，资源利用率是很低的。
        // 原因在于从磁盘中读取文件的时候，大部分的CPU时间用于"等待磁盘去读取数据"。
        // 在这段时间里，CPU非常的空闲。其深层次的原因是对于IO操作，
        // 往往是通过"硬件直接存取器(DMA)"来执行的，也就是说，
        // CPU只需要将发送一个指令给DMA去执行对应的IO操作即可，指令发送是一瞬间的事，
        // 发送完成CPU就可以干其他的事了,我们说的IO操作需要执行5秒事实上是DMA执行这个操作需要5秒的时间，
        // 而不是CPU。
        //因此CPU现在很空闲，它可以做一些别的事情。
    }

    /**
     * 通过改变操作的顺序，就能够更好的使用CPU资源,
     *
     * CPU等待第一个文件被读取完。然后开始读取第二个文件。
     * 当第二文件在被读取的时候，CPU会去处理第一个文件
     *
     * 我们将B文件的操作放在了另外一个线程中执行,所以效率可以得到提升。
     * 这是因为我们在A文件读取完成之后，同时开始了A文件的处理和B文件的读取工作
     */
    @Test
    public void method2() throws InterruptedException {
        final long start = System.currentTimeMillis();
        System.out.println("----------程序开始运行---------");
        System.out.println("读取A文件开始...");
        Thread.currentThread().sleep(5000);
        System.out.println("读取A文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理A文件，同时开始读取B文件..");
        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("读取B文件开始...");
                    Thread.currentThread().sleep(5000);
                    System.out.println("读取B文件结束，耗时："+(System.currentTimeMillis()-start)/1000+"秒...开始处理B文件");
                    Thread.currentThread().sleep(2000);
                    System.out.println("B文件处理完成...");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t1.start();
        Thread.currentThread().sleep(2000);
        System.out.println("A文件处理完成...");
        t1.join();
        System.out.println("总耗时:"+(System.currentTimeMillis()-start)/1000+"秒");
    }

    /**
     * 还有继续改进的空间。因为现在我们是在A文件读取完成
     * 之后在读取B文件
     *
     * 我们完全可以同时开启两个线程，两个线程一个用于读取和处理A文件，
     * 一个用于读取和处理B文件。
     *
     *  记住，在等待磁盘读取文件的时候，CPU大部分时间是空闲的。
     */
    @Test
    public void method3() throws InterruptedException {
        final long start = System.currentTimeMillis();

        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    System.out.println("开始读取B文件......");
                    Thread.currentThread().sleep(5000);
                    System.out.println("B文件读取完毕，耗时: " + (System.currentTimeMillis() - start)/1000 + "秒");
                    System.out.println("开始处理B文件");
                    Thread.currentThread().sleep(2000);
                    System.out.println("B文件处理完成,耗时: " + (System.currentTimeMillis() - start)/1000 + "秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        System.out.println("开始读取A文件......");
        Thread.currentThread().sleep(5000);
        System.out.println("A文件读取完毕，耗时: " + (System.currentTimeMillis() - start)/1000 + "秒");
        System.out.println("开始处理A文件");
        Thread.currentThread().sleep(2000);
        System.out.println("A文件处理完成,耗时: " + (System.currentTimeMillis() - start)/1000 + "秒");
        // t1.join()是用来将t1这个线程加入到主线程中，主线程就会等待t1线程执行完毕在继续执行
        t1.join();
        System.out.println("所有文件处理完成，耗时: " + (System.currentTimeMillis() - start)/1000 + "秒");
    }
}

package org.java.core.base.concurrent.chapter1;

public class Resource {

    public static void main(String[] args) throws InterruptedException {
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
        // 才会重新请求CPU执行(重新抢占cpu)。因为我们的代码都是在main方法即主线程中运行，
        // 因此当主线程休眠的时候，就相当于程序停止了运行，
        // 等到休眠时间已过，程序才会继续运行，然后又休眠,运行...。

        // 需要注意的是，上面的代码，资源利用率是很低的。
        // 原因在于从磁盘中读取文件的时候，大部分的CPU时间用于"等待磁盘去读取数据"。
        // 在这段时间里，CPU非常的空闲。其深层次的原因是对于IO操作，
        // 往往是通过"硬件直接存取器(DMA)"来执行的，也就是说，
        // CPU只需要将发送一个指令给DMA去执行对应的IO操作即可，指令发送是一瞬间的事，
        // 发送完成CPU就可以干其他的事了,我们说的IO操作需要执行5秒事实上是DMA执行这个操作需要5秒的时间，
        // 而不是CPU。

        //因此CPU现在很空闲，它可以做一些别的事情。
    }
}

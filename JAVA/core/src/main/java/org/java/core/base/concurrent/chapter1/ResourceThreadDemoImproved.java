package org.java.core.base.concurrent.chapter1;

public class ResourceThreadDemoImproved {

    /**
     * 还有继续改进的空间。因为现在我们是在A文件读取完成
     * 之后在读取B文件，我们完全可以同时开启两个线程，两个线程一个用于读取和处理A文件，
     * 一个用户读取和处理B文件。
     *
     *  记住，在等待磁盘读取文件的时候，CPU大部分时间是空闲的。
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
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

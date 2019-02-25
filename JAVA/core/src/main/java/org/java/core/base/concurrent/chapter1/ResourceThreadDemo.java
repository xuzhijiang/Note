package org.java.core.base.concurrent.chapter1;

public class ResourceThreadDemo {

    /**
     * 通过改变操作的顺序，就能够更好的使用CPU资源,
     *
     * CPU等待第一个文件被读取完。然后开始读取第二个文件。
     * 当第二文件在被读取的时候，CPU会去处理第一个文件
     *
     * 我们将B文件的操作放在了另外一个线程中执行,所以效率可以得到提升。
     * 这是因为我们在A文件读取完成之后，同时开始了A文件的处理和B文件的处理工作
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
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
}

package org.java.core.base.concurrent.chapter3;

public class ThreadCompetitionDemo2 {
    static int count=0;
    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i <5000000 ; i++) {
                    //我们可以通过synchronized关键字和锁来解决ThreadCompetitionDemo
                    // 中出现的问题
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

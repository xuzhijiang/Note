package org.java.core.base.concurrent.chapter5.CustomAQSComponent.Share;

public class ShareTest {

    public static void main(String[] args) {
        int N = 20;//人工数
        Share share = new Share(5);//机器数

        for(int i=0;i<N;i++){
            new WorkerThread(i, share).start();
        }
    }

    private static class WorkerThread extends Thread {
        private int num;
        private Share share;

        public WorkerThread(int num, Share share) {
            this.num = num;
            this.share = share;
        }

        @Override
        public void run() {
            try {
                // 当前线程要获取一台机器(资源获取)
                share.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人" + this.num + "释放机器");
                // 释放机器(资源)
                share.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

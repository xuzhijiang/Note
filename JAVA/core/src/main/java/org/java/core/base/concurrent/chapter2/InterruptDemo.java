package org.java.core.base.concurrent.chapter2;

import java.util.Date;

public class InterruptDemo{

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                //预期循环10次
                for (int i=0;i<10;i++) {
                    try {
                        Thread.sleep(4000);
                        System.out.println("自定义线程：当前时间：" + new Date().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("自定义线程收到中断信号，总循环了: " + i + "次");
                        // 接收到中断信时，停止运行
                        return;
                    }
                    // 可以看到自定义线程打印出两次当前时间后就停止了运行，
                    // 根本原因在于，我们在收到中断信号后，在catch代码中使用了return，
                    // 结束了方法。读者可以尝试将return去掉，这个时候，
                    // 即使收到了中断信号，也会继续打印10次！
                }
            }
        };
        t.start();

        // 主线程休眠9秒
        Thread.sleep(9000);
        System.out.println("主线程：等到9秒后发送中断信号...");
        t.interrupt();
    }
}

package org.java.core.base.concurrent.chapter2;

public class InterruptDemo2 {

    public static void main(String[] args) throws InterruptedException{
        Thread t = new Thread(){
            @Override
            public void run() {
                int i=0;
                while (true) {
                    //每次打印前都判断是否被中断
                    if(!Thread.interrupted()){
                        i++;
                        System.out.println("自定义线程，打印...."+i+"次");
                    }else{//如果被中断，停止运行
                        System.out.println("自定义线程：被中断...");
                        return;
                    }
                }
            }
        };
        t.start();
        //主线程休眠1毫秒，以便自定义线程执行
        Thread.sleep(1);
        System.out.println("主线程:休眠1毫秒后发送中断信号...");
        t.interrupt();

        // 在收到中断信号后，自定义线程停止了运行。不过，为什么自定义线程t在收到中断信号后还执行了一次呢？
        // 这是因为中断信号发送的后，t线程正好运行到了打印的代码，
        // 因此只有到下一次循环的时候才会检测到中断信号，停止运行。
        //
        // 如果你多运行几次的话，会发现每次的结果都是不一样的。
    }
}

package org.java.core.base.concurrent.chapter2;

public class MultiRunDemo {

    public static void main(String[] args) {
        //传入Runnable实现类
        new Thread(new MyRunnable()){
            @Override
            public void run() {
                System.out.println("我是直接覆写Thread类的run方法。。。。。。");
                super.run();
            }
        }.start();
    }
}

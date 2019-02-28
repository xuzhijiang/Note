package org.java.core.base.concurrent.chapter4;

public class VolatileDemo {

    volatile static Boolean flag = true;

    /**
     * 由于我们使用了volatile关键字，因此自定义线程每次修改时状态变量的值时，
     * 主线程都可以实时的感知到。
     * @param args
     */
    public static void main(String[] args){
        //该线程每隔1毫秒，修改一次flag的值
        new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.currentThread().sleep(1);
                        flag=!flag;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // 对于多个线程都依赖于同一个状态变量的值来判断是否要执行某段代码时，
        // 使用volatile关键字更为有用，其可以保证多个线程在任一时刻的行为都是一致的。
        while(true){
            if(flag){
                System.out.println("do some thing...");
            }else{
                System.out.println(".....");
            }
        }
    }
}

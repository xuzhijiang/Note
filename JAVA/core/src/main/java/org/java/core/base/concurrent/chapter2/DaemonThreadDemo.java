package org.java.core.base.concurrent.chapter2;

import java.io.File;
import java.io.FileOutputStream;

public class DaemonThreadDemo {

    public static void main(String[] args) {
        Runnable tr = new TestRunnable();
        Thread t = new Thread(tr);
        // 设置守护线程注释掉，文件daemon.txt是可以被写入daemon字符串的
        // 如果不注释，daemon.txt是无法写入字符串的
        t.setDaemon(true);//设置守护线程
        t.start();
    }
}

class TestRunnable implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("我是TestRunnable: " + Thread.currentThread().getName());
            Thread.sleep(1000);//守护线程阻塞1秒后执行
            File f = new File("D:\\daemon.log");
            FileOutputStream fos = new FileOutputStream(f, true);
            fos.write("daemon".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

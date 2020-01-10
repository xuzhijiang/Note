package org.java.core.DesignPatterns.singleton.test;

import org.java.core.DesignPatterns.singleton.Singleton4;

import java.util.concurrent.*;

public class Singleton4Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println("********单线程没有问题**********");
//        Singleton4 s1 = Singleton4.getInstance();
//        Singleton4 s2 = Singleton4.getInstance();
//        System.out.println(s1 == s2);
//        System.out.println(s1);
//        System.out.println(s2);

        System.out.println("**************************");

        System.out.println("****************多线程就会出现问题");
        Callable<Singleton4> c = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {
                return Singleton4.getInstance();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);

        Future<Singleton4> future1 = es.submit(c);
        Future<Singleton4> future2 = es.submit(c);

        Singleton4 s3 = future1.get();
        Singleton4 s4 = future2.get();
        System.out.println(s3 == s4);
        System.out.println(s3);
        System.out.println(s4);

        es.shutdown();
    }
}

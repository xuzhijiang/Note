package com.java.datastructure.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AtomicIntegerTest {

    public static void main(String[] args) throws InterruptedException {
        int numOfThreads = 1000;
        int numOfIncrements = 100000;
        ExecutorService service = Executors.newFixedThreadPool(numOfThreads);
        Counter counter = new AtomicCounter();
        long start = System.currentTimeMillis();
        for(int i=0;i<numOfThreads;i++){
            service.submit(new CounterClient(counter, numOfIncrements));
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        long end = System.currentTimeMillis();
        System.out.println("Counter result: " + counter.getCounter());
        System.out.println("耗时: " + (end - start)/1000 + "秒");
    }

}

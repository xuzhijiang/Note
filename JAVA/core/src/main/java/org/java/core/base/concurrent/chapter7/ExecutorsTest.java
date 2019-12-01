package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsTest {

    @Test
    public void createThreadPool() {
        Executors.newCachedThreadPool();
        Executors.newCachedThreadPool(Executors.defaultThreadFactory());

        Executors.newFixedThreadPool(10);
        Executors.newFixedThreadPool(10, Executors.defaultThreadFactory());

        Executors.newSingleThreadExecutor();
        Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
    }

    private static long count = 0L;

}

package org.java.core.base.concurrent.chapter5.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

// 仅当同一个线程已经持有写锁，才允许写锁重入（再次获得写锁）
public class ReadWriteLock3{ // 写锁可重入
    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

    // 已经拿到写锁的线程数目
    private int writeAccesses    = 0;
    // 请求写锁的线程数目
    private int writeRequests    = 0;
    private Thread writingThread = null;

    public synchronized void lockWrite() throws InterruptedException{
        writeRequests++;
        Thread callingThread = Thread.currentThread();
        while(!canGrantWriteAccess(callingThread)){
            wait();
        }
        writeRequests--;
        writeAccesses++;
        writingThread = callingThread;
    }

    public synchronized void unlockWrite()
            throws InterruptedException{
        writeAccesses--;
        if(writeAccesses == 0){
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        // 如果当前有读的线程，那么直接返回false
        if(hasReaders()) return false;
        // 当前写的线程为null，意味着可以获得写锁
        if(writingThread == null)    return true;
        // callingThread是否是当前写的线程
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }
}
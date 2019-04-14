package org.java.core.base.concurrent.chapter5.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

// 有时，我们希望一个拥有读锁的线程，也能获得写锁。
// 想要允许这样的操作，要求这个线程是唯一一个拥有读锁的线程
public class ReadWriteLock4 { //读锁升级到写锁

    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

    private int writeAccesses    = 0;
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

    public synchronized void unlockWrite() throws InterruptedException{
        writeAccesses--;
        if(writeAccesses == 0){
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        // 判断当前线程是否拥有读锁,如果拥有，那么这个线程也能获取写锁.
        if(isOnlyReader(callingThread)) return true;

        // 是否拥有读的线程
        if(hasReaders()) return false;

        if(writingThread == null) return true;

        // 当前线程callingThread是否是 正在拥有写锁writingThread的线程
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }

    private boolean isOnlyReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }
}
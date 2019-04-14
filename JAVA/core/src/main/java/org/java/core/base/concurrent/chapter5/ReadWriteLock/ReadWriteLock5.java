package org.java.core.base.concurrent.chapter5.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

// 有时拥有写锁的线程也希望得到读锁。如果一个线程拥有了写锁，
// 那么自然其它线程是不可能拥有读锁或写锁了。所以对于一个拥有写锁的线程，再获得读锁，是不会有什么危险的
public class ReadWriteLock5 {//写锁降级到读锁

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
        // 判断当前线程是否拥有读锁
        if(isOnlyReader(callingThread)) return true;

        // 是否拥有读的线程
        if(hasReaders()) return false;

        if(writingThread == null) return true;

        // 当前线程是否是写线程
        if(!isWriter(callingThread)) return false;
        return true;
    }

    private boolean canGrantReadAccess(Thread callingThread){
        if(isWriter(callingThread)) return true;
        if(writingThread != null) return false;
        if(isReader(callingThread)) return true;
        if(writeRequests > 0) return false;
        return true;
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }

    private boolean isOnlyReader(Thread callingThread){
        return readingThreads.get(callingThread) != null && readingThreads.get(callingThread) == 1;
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }
}
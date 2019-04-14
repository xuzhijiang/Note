package org.java.core.base.concurrent.chapter5.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

// 完整的ReadWriteLock实现,从1-5的演变

// 在finally中调用unlock()

// 在利用ReadWriteLock来保护临界区时，如果临界区可能抛出异常，在finally块中调用readUnlock()和writeUnlock()就显得很重要了。
// 这样做是为了保证ReadWriteLock能被成功解锁，然后其它线程可以请求到该锁。这里有个例子：

/**
 * lock.lockWrite();
 * try{
 * 	//do critical section code, which may throw exception
 * } finally {
 * 	lock.unlockWrite();
 * }
 */
// 上面这样的代码结构能够保证临界区中抛出异常时ReadWriteLock也会被释放。如果unlockWrite方法不是在finally块中调用的，
// 当临界区抛出了异常时，ReadWriteLock 会一直保持在写锁定状态，就会导致所有调用lockRead()或lockWrite()的线程一直阻塞。
// 唯一能够重新解锁ReadWriteLock的因素可能就是ReadWriteLock是可重入的，当抛出异常时，这个线程后续还可以成功获取这把锁，
// 然后执行临界区以及再次调用unlockWrite()，这就会再次释放ReadWriteLock。但是如果该线程后续不再获取这把锁了呢？
// 所以，在finally中调用unlockWrite对写出健壮代码是很重要的。
public class ReadWriteLock{
    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

    private int writeAccesses    = 0;
    private int writeRequests    = 0;
    private Thread writingThread = null;

    public synchronized void lockRead() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(! canGrantReadAccess(callingThread)){
            wait();
        }

        readingThreads.put(callingThread, (getReadAccessCount(callingThread) + 1));
    }

    /**
     * 是否可以获取读锁
     * @param callingThread
     * @return
     */
    private boolean canGrantReadAccess(Thread callingThread){
        // 当前线程获取了写锁，所以可以获取读锁
        if(isWriter(callingThread)) return true;
        // 当前线程没有获取读锁，而且有其他线程获取了读锁，所以不能够获取读锁
        if(hasWriter()) return false;
        // 当前thread已经获取了读锁，所以可以再次获取
        if(isReader(callingThread)) return true;
        // 当前有线程请求写锁，所以不能获取读锁,要让当前现成休眠
        if(hasWriteRequests()) return false;
        return true;
    }

    /**
     * 释放read lock
     */
    public synchronized void unlockRead(){
        Thread callingThread = Thread.currentThread();
        if(!isReader(callingThread)){
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold a read lock on this ReadWriteLock");
        }
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount <= 1){
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount -1));
        }
        notifyAll();
    }

    public synchronized void lockWrite()
            throws InterruptedException{
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
        if(!isWriter(Thread.currentThread())){
            throw new IllegalMonitorStateException(
                    "Calling Thread does not" +
                            " hold the write lock on this ReadWriteLock");
        }
        writeAccesses--;
        if(writeAccesses == 0){
            writingThread = null;
        }
        notifyAll();
    }

    private boolean canGrantWriteAccess(Thread callingThread){
        // 当前线程拥有读锁，所以当前线程可以获取写锁
        if(isOnlyReader(callingThread)) return true;
        // 如果当前有线程已经拿到了读锁，那么当前线程就不能获得写锁，那么，就休眠
        if(hasReaders()) return false;
        // 当前获取写锁的线程为null，也就是当前没有线程获取到写锁
        if(writingThread == null) return true;
        // 如果当前线程不是已经拿到写锁的现成，那么当前现成也不能获得写锁
        if(!isWriter(callingThread)) return false;
        return true;
    }


    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        if(accessCount == null) return 0;
        return accessCount.intValue();
    }

    private boolean hasReaders(){
        return readingThreads.size() > 0;
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }

    private boolean isOnlyReader(Thread callingThread){
        return readingThreads.size() == 1 &&
                readingThreads.get(callingThread) != null;
    }

    private boolean hasWriter(){
        return writingThread != null;
    }

    private boolean isWriter(Thread callingThread){
        return writingThread == callingThread;
    }

    private boolean hasWriteRequests(){
        return this.writeRequests > 0;
    }
}
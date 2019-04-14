package org.java.core.base.concurrent.chapter5.ReadWriteLock;

import java.util.HashMap;
import java.util.Map;

// 要保证某个线程中的读锁可重入，要么满足获取读锁的条件（没有线程持有写锁并且没有线程有写请求），
// 要么这个线程已经持有读锁（不管是否有写请求）

// 本例解决: 当一个已经持有读锁的线程再次请求读锁时，就会被阻塞。(实现读锁可重入)
public class ReadWriteLock2{// 读锁可重入

    // map存储一个线程已经持有读锁的次数，
    // 当需要判断某个线程能否获得读锁时，就利用map中存储的数据进行判断
    private Map<Thread, Integer> readingThreads = new HashMap<Thread, Integer>();

    // 拥有读锁的线程的个数
    private int readers = 0;

    // 拥有写锁的线程的个数
    private int writers = 0;
    // 请求写锁的线程
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException{
        Thread callingThread = Thread.currentThread();
        while(!canGrantReadAccess(callingThread)){
            wait();
        }
        readingThreads.put(callingThread, (getReadAccessCount(callingThread) + 1));
    }

    public synchronized void unlockRead(){
        Thread callingThread = Thread.currentThread();
        int accessCount = getReadAccessCount(callingThread);
        if(accessCount <= 1) {
            readingThreads.remove(callingThread);
        } else {
            readingThreads.put(callingThread, (accessCount -1));
        }
        notifyAll();
    }

    private boolean canGrantReadAccess(Thread callingThread){
        // 只有在没有线程拥有写锁的情况下才允许读锁的重入
        if(writers > 0)
            return false;

        // 当前线程是否已经持有了读锁
        if(isReader(callingThread))
            return true;

        // 如果当前有线程请求写锁，那么当前线程不能获得读锁
        if(writeRequests > 0) return false;
        return true;
    }

    private int getReadAccessCount(Thread callingThread){
        Integer accessCount = readingThreads.get(callingThread);
        if(accessCount == null) return 0;
        return accessCount.intValue();
    }

    private boolean isReader(Thread callingThread){
        return readingThreads.get(callingThread) != null;
    }

    public synchronized void lockWrite()
            throws InterruptedException{
        // 把写锁请求数加1(请求写锁的线程数)
        writeRequests++;

        // 判断是否能够真能获得写锁，当没有线程持有读锁（readers==0 ）,
        // 且没有线程持有写锁（writers==0）时就能获得写锁。有多少线程在请求写锁并无关系。
        while(readers > 0 || writers > 0){
            wait();
        }
        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite()
            throws InterruptedException{
        writers--;
        // 防止信号丢失
        notifyAll();
    }

    public static void main(String[] args) {
        HashMap<Object, Integer> map = new HashMap<>();
        System.out.println(map.get("aaa"));
    }
}
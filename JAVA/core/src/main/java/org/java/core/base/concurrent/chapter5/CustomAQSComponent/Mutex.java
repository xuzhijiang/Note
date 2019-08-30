package org.java.core.base.concurrent.chapter5.CustomAQSComponent;

// 不可重入的独占锁接口
public interface Mutex {

    //获取锁
    public void lock();

    //释放锁
    public void release();
}

package org.java.core.base.concurrent.chapter7;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.FutureTask;

/**
 * Future是用来执行任务的结果，JDK自带的Future实现FutureTask，只能同步等待结果，
 * 当get方法被调用的时候，当前线程就会被阻塞，一直到任务执行完成，或者一直等待到超时。
 *
 * 在Netty中，提供了另外一种Future实现(ChannelFuture)，其是异步的，也就是说不需要同步等待执行结果，
 * 其可以在任务执行完成之后，回调用户指定的方法，以告诉我们任务的结果。
 *
 * 当然，这里并不是会去分析Netty中这种ChannelFuture的实现源码，我们只是要实现一个类似功能的Future实现，
 * 在这里称之为SmartFuture。其支持同步等待返回结果，也支持异步通知结果。
 * 由于FutureTask已经提供了同步等待的功能，所以我们只需要让我们的SmartFuture继承FutureTask，
 * 再添加相关异步功能的方法即可。
 * @param <V>
 */
public class SmartFuture<V> extends FutureTask<V> {

    //异步通知的listener
    private Set<SmartFutureListener> listeners=null;

    //任务运行结果
    Object result=null;
    private boolean hasResult;

    public SmartFuture(Callable<V> callable) {
        super(callable);
        listeners=new CopyOnWriteArraySet<SmartFutureListener>();
    }

    public SmartFuture(Runnable runnable, V result) {
        super(runnable, result);
        listeners=new CopyOnWriteArraySet<SmartFutureListener>();
    }

    /**
     * 用户在往线程池中提交任务后，可以获取到SmartFuture对象，通过调用其addListener方法，
     * 添加监听器，SmartFuture 在执行完成时，会调用SmartFutureListener的指定方法：
     * @param listener
     */
    public void addListener(SmartFutureListener listener){
        if(listener==null){
            throw new NullPointerException();
        }
        if(hasResult){//如果添加listener的时候，任务已经执行完成，直接回调listener
            notifyListener(listener);
        }else{//如果任务没有执行完成，添加到监听队列
            listeners.add(listener);
        }
    }

    //覆写set方法，结果运行成功
    @Override
    protected void set(V v) {
        super.set(v);
        result=v;
        hasResult=true;
        notifyListeners();
    }

    //覆写 setException方法
    @Override
    protected void setException(Throwable t) {
        super.setException(t);
        result=t;
        hasResult=true;
        notifyListeners();
    }

    //回调
    private void notifyListeners() {
        for (SmartFutureListener listener : listeners) {
            notifyListener(listener);
        }
    }

    private void notifyListener(SmartFutureListener listener) {
        if(result instanceof Throwable){
            listener.onError((Throwable) result);
        }else{
            listener.onSuccess(result);
        }
        listeners=null;
    }
}

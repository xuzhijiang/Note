package org.java.core.base.concurrent.chapter7;

import org.junit.Test;

import java.util.Set;
import java.util.concurrent.*;

/**
 * JDK自带的Future实现FutureTask，只能同步等待结果，
 * 当get方法被调用的时候，当前线程就会被阻塞，一直到任务执行完成，或者一直等待到超时。
 *
 * 在Netty中，提供了另外一种Future实现(ChannelFuture)，其是异步的，也就是说不需要同步等待执行结果，
 * 其可以在任务执行完成之后，回调用户指定的方法，以告诉我们任务的结果。
 *
 * 当然，这里并不是会去分析Netty中这种ChannelFuture的实现源码，
 * 我们只是要实现一个类似的Future实现，
 * 在这里称之为SmartFuture。其支持同步等待返回结果，也支持异步通知结果。
 * 由于FutureTask已经提供了同步等待的功能，所以我们只需要让我们的SmartFuture继承FutureTask，
 * 再添加相关异步功能的方法即可。
 */
public class SmartFuture<V> extends FutureTask<V> {

    // 异步通知的listener
    private Set<SmartFutureListener> listeners;

    //任务运行结果
    Object result;

    private boolean hasResult;

    public SmartFuture(Callable<V> callable) {
        super(callable);
        listeners=new CopyOnWriteArraySet<>();
    }

    public SmartFuture(Runnable runnable, V result) {
        super(runnable, result);
        listeners=new CopyOnWriteArraySet<>();
    }

    /**
     * 用户在往线程池中提交任务后，可以获取到SmartFuture对象，
     * 通过调用其addListener方法，添加监听器，SmartFuture 在执行完成时，会调用SmartFutureListener的指定方法：
     */
    public void addListener(SmartFutureListener listener){
        if(listener==null){
            throw new NullPointerException();
        }
        // 如果添加listener的时候，任务已经执行完成，直接回调listener
        if(hasResult){
            notifyListener(listener);
        }else{//如果任务没有执行完成，添加到监听队列
            listeners.add(listener);
        }
    }

    //覆写set方法，结果运行成功时callback
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

    // 回调
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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SmartThreadExecutorPool smartThreadExecutorPool =
                new SmartThreadExecutorPool(5,10,
                        10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        //提交一个任务
        SmartFuture<String> smartFuture = smartThreadExecutorPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                return "当前时间：" + System.currentTimeMillis();
            }
        });

        smartFuture.addListener(new SmartFutureListener<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("asynchronous callback success: "+result);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("asynchronous callback fail: "+throwable);
            }
        });


        String syncResult = null;
        try {
            syncResult = smartFuture.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("[同步]回调成功(synchronous callback success!!)："+syncResult);
    }
}

interface SmartFutureListener<V> {
    void onSuccess(V result);
    void onError(Throwable throwable);
}

/**
 * 要实现异步监听的Future，我们除了要实现Future对象，还要自己实现一个线程池，
 * 因为如果我们直接使用ThreadExecutorPool提交任务(Callable，Runnable)，
 * 其还是会将其包装成一个通过newFutureTask方法创建的FutureTask对象，
 * 我们可以对相关方法进行覆盖，使得返回的对象是SmartFuture
 */
class SmartThreadExecutorPool extends ThreadPoolExecutor {

    public SmartThreadExecutorPool(int corePoolSize, int maximumPoolSize,
                                   long keepAliveTime, TimeUnit unit,
                                   BlockingQueue<Runnable> workQueue)
        {super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);}

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new SmartFuture<>(runnable, value);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new SmartFuture<>(callable);
    }

    // 覆写这三个方法只是为了在使用的时候，方便一点，不需要将Future强转为SmartFuture
    @Override
    public SmartFuture<?> submit(Runnable task) {
        return (SmartFuture<?>) super.submit(task);
    }

    @Override
    public <T> SmartFuture<T> submit(Runnable task, T result) {
        return (SmartFuture<T>) super.submit(task, result);
    }

    @Override
    public <T> SmartFuture<T> submit(Callable<T> task) {
        return (SmartFuture<T>) super.submit(task);
    }
}
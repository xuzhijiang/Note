# Callable诞生的背景?

    与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装
    
    当需要线程带返回值的时候,使用Callable

    起了100个线程,98个线程执行正确,剩余2个执行错了,这两个错误的原因是什么,可坑需要把错误返回回来.

# Callable和Runnable的区别?

- Callable是带返回值的,Runnable没有返回值
- Callable为泛型类型接口,call方法的返回值就是泛型类型
- Callable的call()方法默认抛异常,Runnable的run()方法默认不抛异常

# Thread和Callable不认识,怎么放到一起呢?

通过一个中间人,适配模式,FutureTask.

# Callable怎么使用?

在ExecutorService接口中声明了若干个submit方法的重载版本,暂时只需要知道Callable一般是和ExecutorService配合来使用的

```java
@FunctionalInterface
public interface Callable<V> {
    // Computes a result, or throws an exception if unable to do so.
    V call() throws Exception;
}

public interface ExecutorService extends Executor {
    <T> Future<T> submit(Callable<T> task);
    <T> Future<T> submit(Runnable task, T result);
    Future<?> submit(Runnable task);
}
```

# Future

```java
public interface Future<V> {
    // cancel方法：用来取消任务，取消任务成功返回true，取消任务失败返回false。
    // 参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，
    // 如果设置true，则表示可以取消正在执行过程中的任务。
    // 1. 如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，
    // 2. 如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；
    // 3. 如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
    boolean cancel(boolean mayInterruptIfRunning);
    // 表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
    boolean isCancelled();
    // isDone方法：表示任务是否已经完成，若任务完成，则返回true；
    boolean isDone();
    // 用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
    V get() throws InterruptedException, ExecutionException;
    // 用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
    V get(long timeout, TimeUnit unit);
}
```
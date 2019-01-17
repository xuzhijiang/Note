Java thread pool它包含一个队列，可以使任务等待执行,
我们可以使用ThreadPoolExecutor在java中创建线程池。

Java线程池管理 Runnable thread的集合，工作线程从队列中执行Runnable。 
java.util.concurrent.Executors提供java.util.concurrent.Executor接口的实现，
以在java中创建线程池

------------------------------------------------------------------------
Executors类使用ThreadPoolExecutor提供ExecutorService的简单实现，
Executors中的提供的实现代码如下:		

//public static ExecutorService newFixedThreadPool(int nThreads) {
//    return new ThreadPoolExecutor(nThreads, nThreads,
//                                  0L, TimeUnit.MILLISECONDS,
//                                  new LinkedBlockingQueue<Runnable>());
//}

但ThreadPoolExecutor提供了更多功能:

ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
	BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
    RejectedExecutionHandler handler)
    
1. 我们可以指定在创建ThreadPoolExecutor实例时将处于活动状态的线程数(corePoolSize)
2. 我们可以限制线程池的大小(maximumPoolSize)
3. 创建我们自己的RejectedExecutionHandler实现来处理不适合工作队列的作业。

ThreadPoolExecutor提供了几种方法，我们可以使用它们找出ThreadPoolExecutor的当前状态，
池大小，活动线程数,任务计数等等。所以我有一个监视器线程，它将以特定的时间间隔打印执行程序信息。

corePoolSize: 要保留在池中的线​​程数，即使这些线程处于空闲状态也要在池中，除非设置了{@code allowCoreThreadTimeOut}
maximumPoolSize：池中允许的最大线程数
keepAliveTime： 当线程数大于corePoolSize时，这是多余的 空闲线程(注意这些线程都是空闲的，已经没有任务的)
在终止之前 等待新任务的最长时间。
unit: @code keepAliveTime 参数的时间单位
workQueue: workQueue队列，用于在执行任务之前保存任务。
此队列仅包含由{@code execute}方法提交的{@code Runnable}任务
threadFactory：当executor创建新线程时使用的工厂
handler: 处理程序在执行时被阻止使用的handler，因为达到了pool的容量和队列容量
# Executor

    起到线程管理作用的类称之为Executor。Executor接口相关的3个主要接口:

![](../pics/Executor相关接口.png)

![](../pics/JDK中的executors的相关类.png)

- `java.util.concurrent.Executor`是顶级接口，其只定义了一个`void execute(Runnable command)`方法，用于接受可以执行的任务
- `public interface ExecutorService extends Executor {}`接口在Executor的基础上添加了一些生命周期方法，例如shutdown等.
- `public interface ScheduledExecutorService extends ExecutorService {}`进一步扩展，添加了定时运行任务的方法

>我们向executors(线程池)中提交任务(Runnable或者Callable接口的实现类)，executors会将提交的任务放入任务队列中。然后会从池中选择一个空闲的线程来处理队列中的任务

![线程池的工作原理](../pics/线程池的工作原理.png)

    线程池的相关概念：线程工厂，线程池以及任务队列

![JDK中的executors的相关类](../pics/JDK中的executors的相关类.png)

# 线程池的execute()方法和submit()方法的区别

- execute()方法用于提交不需要返回值的任务，无法判断提交的任务是否成功执行.
- submit()方法用于提交需要返回值的任务。线程池会返回一个Future类型的对象，通过这个Future对象可以判断任务是否执行成功并且可以通过future的get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，而使用 `get（long timeout，TimeUnit unit）`方法则会阻塞当前线程一段时间后立即返回，这时候有可能任务没有执行完。
  
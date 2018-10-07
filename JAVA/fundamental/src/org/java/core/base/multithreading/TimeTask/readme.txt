Java java.util.Timer是一个工具类，可用于调度某个线程在将来的某个时间被执行。 
Java Timer类可用于调度一个任务运行一次或定期运行。

java.util.TimerTask是一个实现Runnable接口的抽象类，
我们需要扩展这个类来创建我们自己的TimerTask，它可以使用java Timer类进行调度

Java Timer类是线程安全的，多个线程可以共享一个Timer对象，而无需外部同步。 
Timer类使用java.util.TaskQueue以给定的间隔添加任务，
并且在任何时候只能有一个线程运行TimerTask，
例如，如果要创建一个每10秒运行一次的Timer，但单线程执行需要20秒， 然后Timer对象将继续向队列添加任务，
一旦一个线程完成，它将通知队列，另一个线程将开始执行,也就是要等到一个线程执行完成，下一个任务才能开始执行。

Java Timer类使用Object wait和notify方法来安排任务。

0. 可以创建Java Timer对象以将相关任务作为守护程序线程运行。
1. Timer cancel（）方法用于终止计时器并丢弃任何计划任务，但它不会干扰当前正在执行的任务并让它完成。
2. 如果计时器Timer作为守护程序线程运行，无论我们是否取消它，它将在所有用户线程完成执行后立即终止。
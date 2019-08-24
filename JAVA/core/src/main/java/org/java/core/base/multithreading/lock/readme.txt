一下是一些Java Lock API中重要的接口和类：

Lock(is a interface):
多数情况下，我们使用synchronized关键字就可以了，但它有一些缺点导致
Java Concurrency包中引入Lock。 Java 1.5 Concurrency 
API提供了带有Lock接口的java.util.concurrent.locks包和一些实现类来改进Object锁定机制。

Condition(is a interface):
Condition对象始终由Lock对象创建。一些重要的方法是await()，类似于wait(),
signal()，signalAll()，类似于notify（）和notifyAll（）方法。

ReadWriteLock(is a interface)：
它包含一对关联的锁，一个用于只读操作，另一个用于写入。
只要没有写入的线程，读锁定可以由多个读取的线程同时持有。写锁是独占的。

ReentrantLock:(is a class):
最广泛使用的Lock接口的实现类，此类以与synchronized关键字类似的方式实现Lock接口。
除了Lock接口实现之外，ReentrantLock还包含一些实用程序方法来获取持有锁的线程，线程等待获取锁等。

synchronized块本质上是可重入的(reentrant)，即如果一个线程锁定了监视器对象，
并且另一个同步块需要锁定在同一监视器对象上，则线程可以进入该代码块。
我认为这就是起名为ReentrantLock的原因.

Java Lock vs synchronized:

1. Java Lock API为锁定提供了更多的可见性和选项，
不像在线程可能最终无限期地等待锁定的同步，我们可以使用tryLock（）来确保线程仅等待特定时间。
2. Synchronization code更清晰，易于维护，而使用Lock我们不得不
使用try-finally块来确保即使在lock（）和unlock（）方法调用之间抛出异常也会释放Lock。
3. synchronization blocks or methods只能覆盖一种方法，
而我们可以在一个方法中获取锁，并使用Lock API在另一个方法中释放它。
4. synchronized keyword不提供公平性，而我们可以在创建
ReentrantLock对象时将公平性设置为true，以便最长等待的线程首先获得锁，
我们可以为Lock创建不同的条件，不同的线程可以针对不同的条件等待（），例如，如果是读线程，可以不用等待，
如果是写线程就需要等待。
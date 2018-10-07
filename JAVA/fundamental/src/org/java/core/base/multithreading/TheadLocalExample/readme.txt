Java ThreadLocal用于创建线程局部变量。 我们知道Object的所有线程都共享它的变量，
因此该变量不是线程安全的。 我们可以使用synchronization来实现线程安全，但是如果我们想避免synchronization
，我们可以使用ThreadLocal变量。

每个线程都有自己的ThreadLocal变量，他们可以使用它的get（）和set（）方法来获取默认值或将其值更改为Thread的本地值。

that wish to associate state with a thread.
ThreadLocal实例通常是私有的静态的在类中的字段，这个字段希望去和线程关联状态。
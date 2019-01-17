Object类包含三个final方法(不允许覆盖的方法)，允许线程就资源的锁状态进行通信。
分别是:  wait(), notify() and notifyAll().

在任何对象上调用这些方法的当前线程应该具有对象监视器，
否则它会抛出java.lang.IllegalMonitorStateException异常。


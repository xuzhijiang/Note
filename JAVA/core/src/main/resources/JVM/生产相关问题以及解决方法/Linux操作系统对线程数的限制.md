## Linux操作系统对线程数的限制

![](../../pics/不同线程模型的限制.png)

    查看linux操作系统使用的是哪一种线程模型: getconf GNU_LIBPTHREAD_VERSION

![](../../pics/系统资源对线程数量的限制.png)

    查看操作系统默认情况下线程栈的大小： ulimit -s
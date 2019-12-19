# ConcurrentModificationException(并发修改异常)详解

## 总结

防止多线程同时迭代一个集合的时候,其中一个线程修改了集合,但是其他线程没有发现,而引入的异常.主要还是为了防止多线程读到的数据不一致,当然单线程使用不当也会引发这个异常.

![](../pics/ConcurrentModificationException详解01.png)
![](../pics/ConcurrentModificationException详解02.png)
![](../pics/ConcurrentModificationException详解03.jpg)
![](../pics/ConcurrentModificationException详解04.png)
![](../pics/ConcurrentModificationException详解05.png)
![](../pics/ConcurrentModificationException详解06.png)
![](../pics/ConcurrentModificationException详解07.png)
![](../pics/ConcurrentModificationException详解08.png)
![](../pics/ConcurrentModificationException详解09.png)
![](../pics/ConcurrentModificationException详解10.png)
![](../pics/ConcurrentModificationException详解11.png)
![](../pics/ConcurrentModificationException详解12.png)
![](../pics/ConcurrentModificationException详解13.png)
![](../pics/ConcurrentModificationException详解14.png)
![](../pics/ConcurrentModificationException详解15.png)
![](../pics/ConcurrentModificationException详解16.png)
![](../pics/ConcurrentModificationException详解17.png)
![](../pics/ConcurrentModificationException详解18.png)
![](../pics/ConcurrentModificationException详解19.png)
![](../pics/ConcurrentModificationException详解20.png)

# 来源

- [https://www.jianshu.com/p/c5b52927a61a](https://www.jianshu.com/p/c5b52927a61a)
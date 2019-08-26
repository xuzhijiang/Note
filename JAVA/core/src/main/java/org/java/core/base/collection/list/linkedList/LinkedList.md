# LinkedList

添加和删除元素的效率要高于ArrayList，更适用于频繁的插入和实现操作。

![](LinkedList类图.png)

    注意: 通过看java.util.Queue的方法可以得出结论,Queue在Java中的语义就是一个单端队列/单向队列.
    也就是只能从一头入队,另一头出队.而java.util.Deque在Java中的语义是双端队列/双向队列.
    也就是可以从一头入队,也可以从这头出队.(虽然Deque的英文意思是队列/顺序/排队的意思,和它的java语义不太一样)
    LinkList实现了Deque接口，表明可以使用双向队列相关的操作,可以把它用作栈或队列.

![](../../core/Java_collection_list_implementations.jpg)

除了正向迭代器，还可以使用反向迭代器: `descendingIterator()`方法

## LinkedList的特点

内部是使用双向链表实现.它是一个有序集合(以插入顺序存储来维护保持顺序性)，支持重复元素。它以插入顺序存储元素。可以添加任意数量的null元素, 不是线程安全

可以使用Collections.synchronizedList()创建一个同步的LinkedList,没有实现RandomAccess接口。所以我们只能按顺序一个个的访问元素。它不支持通过下标随机访问元素.当我们尝试从LinkedList访问某个元素时，要从LinkedList的开头或结尾开始 搜索该元素。

我们可以使用ListIterator迭代LinkedList元素.

## LinkedList的接口Deque的方法

LinkedList实现了Deque,所以可以使用:

![](../../queue/Deque接口的方法.png)

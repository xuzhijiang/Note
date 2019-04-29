## Queue

### 类的继承关系

```java
public interface Queue<E> extends Collection<E> {}

public interface Collection<E> extends Iterable<E> {}

public interface Iterable<T> {}
```

### 特点

1. 元素是有序的
2. 就像真实世界(real-world)的队列一样，Queue在队列末尾(rear)插入
元素并从队列的开头(front)删除(delete)
4. 遵循FIFO
6. 最常用的Queue实现是LinkedList，ArrayBlockingQueue和PriorityQueue。
7. BlockingQueues不接受null元素。 如果我们执行任何与null相关的操作，它将抛出NullPointerException。
8. BlockingQueues用于实现基于生产者/消费者的应用程序。
9. BlockingQueues是线程安全的。
10.  java.util.concurrent package中可用的Queues是Bounded Queues.
11. 所有Deques都不是线程安全的。
12. ConcurrentLinkedQueue is an unbounded thread-safe Queue based on linked nodes.
ConcurrentLinkedQueue是一个无界的线程安全的基于链接节点的Queue.
13. 除了Deques之外，所有队列都支持在队列尾部插入并在队列的头部删除。
14. Deques是队列，但它们支持两端的元素插入和移除。(Deques are queues but they support element insertion and removal at both ends.)

Deque:

	 (insert or remove)--rear-----------front--->(remove or insert)

Java Queue接口扩展了Collection接口。 集合接口扩展了Iterable接口。
一些常用的Queue实现类是LinkedList，PriorityQueue，ArrayBlockingQueue，
DelayQueue，LinkedBlockingQueue，PriorityBlockingQueue等.
AbstractQueue提供了Queue接口的骨干实现，以减少实现Queue的工作量。

some of the useful and frequently used Java Queue methods:

int size（）：获取Set中的元素数。
boolean isEmpty（）：检查Set是否为空。
boolean contains（Object o）：如果此Set包含指定的元素，则返回true。
Iterator iterator（）：返回此set中元素的迭代器。元素按特定顺序返回。
boolean removeAll（Collection c）：从此set中移除指定collection中包含的所有元素（可选操作）。
boolean retainAll（Collection c）：仅保留此set中包含在指定collection中的元素（可选操作）。
void clear（）：从集合中删除所有元素。
E remove（）：检索并删除此队列的头部。
E poll（）：检索并删除此队列的头部，如果此队列为空，则返回null。
E peek（）：检索但不删除此队列的头部，如果此队列为空，则返回null。
boolean offer（E e）：如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列。
E element（）：检索但不删除此队列的头部。
boolean add（E e）：如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列中，成功时返回true，如果当前没有可用空间则抛出IllegalStateException。
Object [] toArray（）：返回包含此set中所有元素的数组。如果此set对其迭代器返回的元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。

Java Queue Common Operations

	Java Queue supports all operations supported by Collection interface 
	and some more operations. It supports almost all operations in two forms.

1. One set of operations throws an exception if the operation fails.
如果操作失败，则一组操作会引发异常。
2. The other set of operations returns a special value if the operation fails.
如果操作失败，则另一组操作返回特殊值。

The following table explains all Queue common operations briefly:

OPERATION	THROWS EXCEPTION		SPECIAL VALUE
Insert			add(e)					offer(e)
Remove			remove()				poll()
Examine			element()				peek()

Java Queue Categories(Java Queue的分类)

在Java中，我们可以找到许多Queue实现。 我们可以将它们大致分为以下两种:
In Java,we can find many Queue implementations. we can broadly categorize them
into the following two types.

Bounded Queues 有界队列
Unbounded Queues 无界队列

1. 有界队列是有容量限制的队列，这意味着我们需要在创建时提供队列的最大大小。 
例如ArrayBlockingQueue.
2. 无界队列是不受容量限制的队列，这意味着我们不应该提供队列的大小。 例如LinkedList

All Queues which are available in java.util package are Unbounded Queues 
and Queues which are available in java.util.concurrent package are Bounded Queues.

在其他方面，we可以将它们大致分为以下两种类型：

阻止队列(Blocking Queues)
非阻塞队列(Non-Blocking Queues)

实现BlockingQueue接口的所有队列都是BlockingQueues，其余是非阻塞队列。

BlockingQueues阻塞直到它完成它的工作或超时，但Non-BlockingQueues不会。

有些队列是Deques，有些队列是PriorityQueues。

BlockingQueue Operations:

除了Queue的两种操作形式之外，BlockingQueue还支持另外两种形式，如下所示。
In addition to Queue’s two forms of operations, BlockingQueue’s 
supports two more forms as shown below.


OPERATION	THROWS EXCEPTION	SPECIAL VALUE	BLOCKS	TIMES OUT
Insert		add(e)				offer(e)		put(e)	offer(e, time, unit)
Remove		remove()			poll()			take()	poll(time, unit)
Examine		element()			peek()			N/A	N/A

某些操作被阻止，直到它完成它的工作，other操作被阻塞，直到超时。


## Deque(双端队列)




## LinkedList

>它是一个有序集合，支持重复元素。 它以插入顺序存储元素。 它支持添加null元素。 它支持基于索引的操作。
内部实现是使用双向链表。

### 类的继承结构

```java
// LinkedList是List和Deque(双端队列)接口的实现
// 注意：- LinkedList实现List，Deque，Cloneable和Serializable。
// 但它没有实现RandomAccess接口。
class LinkedList<E> extends AbstractSequentialList<E>
implements List<E>, Deque<E>, Cloneable, java.io.Serializable {}
```

### Linked特点

1. 在内部，它是使用双向链表数据结构实现的。
2. 它支持重复元素。
3. 它以插入顺序存储来维护它的元素。
4. 我们可以添加任意数量的null元素。
5. 它不同步意味着它不是线程安全的。
6. 我们可以使用Collections.synchronizedList()方法创建一个同步的LinkedList。
7. 可以将它用作堆栈(stack)或队列(queue)。
8. 它没有实现RandomAccess接口。 所以我们只能按顺序访问元素。 它不支持随机访问元素。
9. 当我们尝试从LinkedList访问元素时，从LinkedList的开头或结尾开始 搜索该元素。
10. 我们可以使用ListIterator迭代LinkedList元素。
11. 从Java SE 8开始，我们可以将LinkedList转换为Stream
12. Java SE 9添加了几个工厂方法来创建一个Immutable LinkedList(不可变的Immutable)。

>以下方法特定于LinkedList类，它们继承自Deque接口：

	void addFirst（E e）：在此列表的开头插入指定的元素。
	void addLast（E e）：在此列表的末尾插入指定的元素。
	E getFirst（）：检索但不删除此列表的第一个元素。此方法与peekFirst的不同之处仅在于，如果此列表为空，则抛出异常。
	E getLast（）：检索但不删除此列表的最后一个元素。此方法与peekLast的不同之处仅在于，如果此列表为空，则抛出异常。
	E remvoeFirst（）：从此列表中删除并返回第一个元素。
	E removeLast（）：从此列表中删除并返回最后一个元素。
	boolean offerFirst（E e）：在此列表的前面插入指定的元素。
	boolean offerLast（E e）：在此列表的末尾插入指定的元素。
	E pollFirst（）：检索并删除此列表的第一个元素，如果此列表为空，则返回null。
	E pollLast（）：检索并删除此列表的最后一个元素，如果此列表为空，则返回null。
	E peekFirst（）：检索但不删除此列表的第一个元素，如果此列表为空，则返回null。
	E peekLast（）：检索但不删除此列表的最后一个元素，如果此列表为空，则返回null。

### LinkedList的使用场景选择

	In this section, we will discuss about what is the best and 
	what is the worst case scenarios to use LinkedList in Java applications.
	在本节中，我们将讨论在Java应用程序中使用LinkedList的最佳情况和最坏情况:
	
	When our frequently used operation is adding or removing 
	elements in the middle of the List, LinkedList is the 
	best class to use.

	Why? Because we don’t need to do more shifts to add 
	or remove elements at the middle of the list. 
	当我们经常使用的操作是在List的中间添加或删除元素时，LinkedList是最好使用的类。
	为什么？ 因为我们不需要做更多的转移来添加或删除列表中间的元素。

	When our frequently used operation is retrieving 
	elements from list, then LinkedList is the worst choice.
	
	Why? Because LinkedList supports only sequential 
	access, does NOT support random access. 
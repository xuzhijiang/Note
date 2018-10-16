class LinkedList<E> extends AbstractSequentialList<E>
implements List<E>, Deque<E>, Cloneable, java.io.Serializable

Java LinkedList是List和Deque(双向队列)接口的实现。 它是常用的List实现类之一。
它不直接从AbstractList类extends。 它extends了AbstractSequentialList class

LinkedList的特点:

	它是一个有序集合，支持重复元素。 它以插入顺序存储元素。 它支持添加null元素。 它支持基于索引的操作。

some of the important points about Java LinkedList:

1. 在内部，它是使用双向链表数据结构实现的。
2. 它支持重复元素。
3. 它以插入顺序存储或维护它的元素。
4. 我们可以添加任意数量的null元素。
5. 它不同步意味着它不是线程安全的。
6. 我们可以使用Collections.synchronizedList（）方法创建一个同步的LinkedList。
7. 在Java应用程序中，我们可以将它用作List，堆栈(stack)或队列(queue)。
8. 它没有实现RandomAccess接口。 所以我们只能按顺序访问元素。 它不支持随机访问元素。
9. 当我们尝试从LinkedList访问元素时，根据元素可用的位置  从LinkedList的开头或结尾开始 搜索该元素。
10. 我们可以使用ListIterator迭代LinkedList元素。
11. 从Java SE 8开始，我们可以将LinkedList转换为Stream，反之亦然。
12. Java SE 9将添加几个工厂方法来创建一个Immutable LinkedList(不可变的Immutable)。

Java LinkedList列表方法:

以下方法继承自List或Collection接口：

	int size（）：获取列表中元素的数量。
	boolean isEmpty（）：检查列表是否为空。
	boolean contains（Object o）：如果此列表包含指定的元素，则返回true。
	Iterator iterator（）：以适当的顺序返回此列表中元素的迭代器。
	Object [] toArray（）：以适当的顺序返回包含此列表中所有元素的数组。
	boolean add（E e）：将指定的元素追加到此列表的末尾。
	boolean remove（Object o）：从此列表中删除指定元素的第一个匹配项。
	boolean retainAll（Collection c）：仅保留此列表中包含在指定集合中的元素。
	void clear（）：从列表中删除所有元素。
	E get（int index）：返回列表中指定位置的元素。
	E set（int index，E element）：用指定的元素替换列表中指定位置的元素。
	ListIterator listIterator（）：返回列表中元素的列表迭代器。
	List subList（int fromIndex，int toIndex）：返回指定fromIndex（包含）
	和toIndex（不包括）之间此列表部分的视图。返回的列表由此列表支持
	，因此返回列表中的非结构更改将反映在此列表中，反之亦然。

以下方法特定于LinkedList类，它们继承自Deque接口：

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

LinkedList的使用场景选择:

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

注意：- LinkedList实现List，Deque，Cloneable和Serializable。
 但它没有实现RandomAccess接口。


Java LinkedList的内部表示:

我们知道，内部Java LinkedList是使用双向链表实现的,
 所以Java LinkedList将它的元素表示为节点(node)。 每个节点(node)分为3个部分:

这里每个节点用于特定目的:

	左侧节点部分用于指向LinkedList中的上一个节点（或元素）。
	右侧节点部分用于指向LinkedList中的下一个节点（或元素）。
	中心节点部件用于存储实际数据。

在JVM中，LinkedList不会按连续顺序存储它的元素。 
它将元素存储在任何可用空间中，并使用左侧和右侧节点部分相互连接，

插入如何在Java LinkedList中工作？
删除如何在Java LinkedList中工作？

Java SE 9 LinkedList
在Java SE 9中，Oracle Corp将添加一些有用的实用方法来创建Immutable List。
 如果您想通过一些有用的示例深入学习它们，
 
 请查看我的帖子：Java SE 9：不可变列表的工厂方法
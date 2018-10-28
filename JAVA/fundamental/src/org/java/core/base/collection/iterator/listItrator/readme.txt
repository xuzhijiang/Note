我们将讨论有关Java的ListIterator的以下概念:


Java Iterator的局限性(Limitations of Java Iterator)
Java ListIterator
Java ListIterator类图(Class Diagram)
Java ListIterator方法
Java ListIterator基本示例(Basic Example)
ListIterator双向迭代示例(Bi-Directional Iteration Example)
Java迭代器的类型(Types of Java Iterators)
Java ListIterator如何在内部工作？(How Java ListIterator works Internally?)
ListIterator的优点(Advantages of ListIterator)
ListIterator的局限性
Iterator和ListIterator之间的相似之处(Similarities between Iterator and ListIterator)
Iterator和ListIterator之间的差异(Differences between Iterator and ListIterator)

与Iterator一样，ListIterator是一个Java Iterator，用于从List实现的对象中逐个迭代元素。

它从Java 1.2开始提供。(It is available since Java 1.2.)
It extends Iterator interface.
它仅对实现List的类有用。(It is useful only for List implemented classes.)
与Iterator不同，它支持所有四种操作(Unlike Iterator, It supports all four operations.)：
CRUD（CREATE，READ，UPDATE和DELETE）。
与Iterator不同，它支持前向和后向迭代。它是一个双向迭代器。
Unlike Iterator, It supports both Forward Direction and Backward Direction iterations.
It is a Bi-directional Iterator.
它没有当前元素(It has no current element);
它的光标位置总是位于调用previous（）返回的元素和调用next（）返回的元素之间。

注意： - Collection API中的CRUD操作是什么？

CREATE：向Collection对象添加新元素。
READ：从Collection对象中检索元素。
UPDATE：更新或设置Collection对象中的现有元素。
DELETE：从Collection对象中删除元素。

public interface ListIterator<E> extends Iterator<E> {}
public interface Iterator<E> {}

为了支持前向和后向迭代和CRUD操作，To support Forward and Backward Direction 
iteration and CRUD operations它具有以下方法。我们可以将这个Iterator
用于所有List实现的类，如ArrayList，CopyOnWriteArrayList，
LinkedList，Stack，Vector等:

void add（E e）：将指定的元素插入列表中。
boolean hasNext（）：如果此列表迭代器在向前遍历列表时具有更多元素，则返回true。
boolean hasPrevious（）：如果此列表迭代器在反向遍历列表时具有更多元素，则返回true。
E next（）：返回列表中的下一个元素并前进光标位置。
int nextIndex（）：返回后续调用next（）返回的元素的索引。
E previous（）：返回列表中的上一个元素并向后移动光标位置。
int previousIndex（）：返回后续调用previous（）返回的元素的索引。
void remove（）：从列表中删除next（）或previous（）返回的最后一个元素。
void set（E e）：用指定的元素替换next（）或previous（）返回的最后一个元素。

Java迭代器的类型
我们知道Java有四个游标：Enumeration，Iterator，ListIterator和Spliterator。 
我们可以将它们分为两种主要类型，如下所示：

单向迭代器
它们是Cursors，仅支持Forward Direction迭代。 例如，
Enumeration，Iterator等是Uni-Directional Iterators。

双向迭代器
它们是支持前向和后向迭代的游标。 例如，ListIterator是Bi-Directional Iterators。

Java ListIterator如何在内部工作？
众所周知，Java ListIterator在两个方向上都有效，这意味着它可以在向前方向和向后方向上工作。 它是一个双向迭代器。 为了支持此功能，它有两组方法。

前向迭代方法
我们需要使用以下方法来支持前向迭代：

hasNext（））
next（）
nextIndex（）

向后方向迭代方法
我们需要使用以下方法来支持向后方向迭代：

hasPrevious（）
previous（）
previousIndex（）

与Iterator不同，ListIterator具有以下优点:

与Iterator一样，它支持READ和DELETE操作。
它也支持CREATE和UPDATE操作。
这意味着，它支持CRUD操作：CREATE，READ，UPDATE和DELETE操作。
它支持正向和反向迭代。这意味着它是一个双向Java游标。
方法名称简单易用。

与Iterator相比，Java ListIterator有许多优点。但是，它仍然存在以下一些限制:

它是Iterator唯一的List实现类。
与Iterator不同，它不适用于整个Collection API。
它不是通用Java游标。
与Spliterator相比，它不支持元素的并行迭代。(it does NOT support Parallel iteration of elements.)
与Spliterator相比，它不支持更好的性能来迭代大量数据。
( it does NOT support better performance to iterate large volume of data.)

Iterator和ListIterator之间的相似之处
在本节中，我们将讨论Java两个游标之间的相似性：Iterator和ListIterator。

bother在Java 1.2中引入。
两者都是用于迭代Collection或List元素的迭代器。
两者都支持READ和DELETE操作。
两者都支持正向迭代。
两者都不是Legacy接口。

Differences between Iterator and ListIterator

ITERATOR										LISTITERATOR
Introduced in Java 1.2.							Introduced in Java 1.2.
It is an Iterator for whole Collection API.		It is an Iterator for only List implemented classes.
It is an Universal Iterator.					It is NOT an Universal Iterator.
It supports only Forward Direction Iteration.	It supports both Forward and Backward Direction iterations.
It’s a Uni-Directional Iterator.				It’s a Bi-Directional Iterator.
It supports only READ and DELETE operations.	It supports all CRUD operations.
We can get Iterator by using iterator() method.	We can ListIterator object using listIterator() method.
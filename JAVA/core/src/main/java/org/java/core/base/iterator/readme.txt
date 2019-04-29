我们将讨论Java枚举的一些基础知识以及关于Java Iterator的深入讨论。 
由于不推荐使用Java Enumeration接口，因此不建议在我们的应用程序中使用它。

我们将讨论关于Java的Iterator的以下概念:

Java Four Cursors
Java Enumeration Limitations
Java Iterator
Java Iterator Class Diagram
Java Iterator Methods
Java Iterator Basic Example
Develop Custom Class Iterator
How Java Iterator Works Internally?
Advantages of Java Iterator
Limitations of Java Iterator
Similarities between Java Enumeration and Iterator
Differences between Java Enumeration and Iterator

Java Four Cursors:

首先，我想定义什么是Java游标？ Java Cursor是一个迭代器，用于逐
个迭代或遍历或检索Collection或Stream对象的元素。

Java支持以下四种不同的游标:

	Enumeration(Java 1.0)
	Iterator(Java 1.2)
	ListIterator(Java 1.2)
	Spliterator(Java 1.8)

每个Java游标都有一些优点和缺点。 我们将在这篇文章中讨论关于枚举的一些基础知识和
关于迭代器的完整细节。 我将在以后的帖子中讨论ListIterator和Spliterator。

Java Enumeration Limitations(Java枚举限制)

Java枚举从Java 1.0开始可用，它有许多限制，不建议在新项目中使用。

它自Java 1.0和传统接口以来可用。
它仅适用于Collection Legacy类。
与其他游标相比，它具有非常冗长的方法名称：hasMoreElements（）和nextElement（）。
在CRUD操作中，它仅支持READ操作。不支持CREATE，UPDATE和DELETE操作。
它仅支持正向迭代。这就是为什么它也被称为Uni-Directional Cursor。
不建议在新的代码库或项目中使用它。

CRUD操作:

CREATE：向Collection对象添加新元素。
READ：从Collection对象中检索元素。
UPDATE：更新或设置Collection对象中的现有元素。
DELETE：从Collection对象中删除元素。

为了克服所有这些问题，Java推出了新的游标：

1. Iterator和ListIterator。(Java 1.2推出)
2. 它在Java 1.8中引入了一种新类型的Cursor：Spliterator。

Java Iterator(Java迭代器)

在Java中，Iterator是java.util包中Collection框架中可用的接口。它是一个Java Cursor，用于迭代一组对象。

它用于逐个遍历集合对象元素。
它从Java 1.2 Collection Framework开始提供。
它适用于所有Collection类。所以它也被称为Universal Java Cursor。
它支持READ和REMOVE操作。
与Enumeration接口相比，Iterator方法名称简单易用。

Java Iterator Class Diagram(Java迭代器类图)

Java Iterator Methods:

boolean hasNext（）：如果迭代有更多元素，则返回true。
E next（）：返回迭代中的下一个元素。
default void remove（）：从底层集合中移除此迭代器返回的最后一个元素。
default void forEachRemaining（Consumer(消费者，使用者) action）：
对每个剩余元素执行给定操作，直到处理完所有元素或操作引发异常。

Develop Custom Class Iterator开发自定义类迭代器

在上一节中，我们讨论了Collection API如何实现iterator（）方法，
以便在使用或不使用Enhanced For Loop的情况下迭代它的元素。

在本节中，我们将讨论如何为User-Defined或Custom类提供类似的功能。 我们应该按照这些说明提供此功能：

1. 定义自定义类。(Define Custom class.)
2. 这个Collection类应该提供Iterable接口方法的实现：iterator（）。
3. 如果我们将这些指令实现到Custom类，那么它就可以使用Enhanced For Loop
或外部Iterator对象来迭代它的元素。

Java Iterator如何在内部工作？

了解Java Iterator及其方法如何在内部工作:

Java Iterator仅支持Forward Direction Iteration， 所以它也被称为单向光标。

Java Iterator的优点
与Enumeration接口相比，Java Iterator具有以下优点或好处。

我们可以将它用于任何Collection类。
它支持READ和REMOVE操作。
它是Collection Cursor for Collection API。
方法名称简单易用。
Java Iterator的局限性
但是，Java Iterator具有以下限制或缺点。

在CRUD操作中，它不支持CREATE和UPDATE操作。
它仅支持正向迭代，即Uni-Directional Iterator。
与Spliterator相比，它不支持并行迭代元素，这意味着它只支持顺序迭代。
与Spliterator相比，它不支持更好的性能来迭代大量数据。
为了克服这些限制，Java又引入了两个游标：ListIterator和Spliterator。我们将在以后的帖子中讨论这两个游标。

Java Enumeration和Iterator之间的相似之处
在本节中，我们将讨论两个Java游标之间的相似之处：Java Enumeration和Iterator

两者都是Java游标。
两者都用于逐个迭代Collection对象元素。
两者都支持READ或Retrieval操作。
两者都是单向Java游标，这意味着仅支持正向迭代。

Java Enumeration和Iterator之间的差异
下表描述了Java Enumeration和Iterator之间的区别:

ENUMERATION							ITERATOR
Introduced in Java 1.0				Introduced in Java 1.2
Legacy Interface					Not Legacy Interface
It is used to iterate only  		Legacy Collection classes.	We can use it for any Collection class.
It supports only READ operation.	It supports both READ and DELETE operations.
It’s not Universal Cursor.			Its a Universal Cursor.
Lengthy Method names.				Simple and easy to use method names.
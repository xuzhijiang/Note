我们知道Queue遵循First-In-First-Out模型，
但有时我们需要根据优先级处理队列中的对象。那是使用
Java PriorityQueue的时候.

假设我们有一个应用程序可以为每日交易时段生成股票报告。
此应用程序处理大量数据并需要时间来处理它。
因此，客户向实际排队的应用程序发送请求，但我们希望先处理高级客户，
然后再处理标准客户。因此，在这种情况下，java中的PriorityQueue实现可能非常有用。

PriorityQueue类是在Java 1.5中引入的，它是Java 
Collections Framework的一部分。

PriorityQueue是基于优先级堆的无界队列，优先级队列的元素默
认按自然顺序排序。我们可以在实例化优先级队列时提供比较器以进行排序。

Java Priority Queue不允许空值，。我们使用java Comparable和
Comparator对对象进行排序，优先级队列使用它们来优先处理它的元素。

优先级队列的头部是基于自然排序或基于比较器的排序的最小元素，如果有多个具有
相同排序的对象，则它可以随机轮询其中任何一个。 当我们轮询队列时，
它从队列中返回头对象。

Java优先级队列大小是无限制的，但我们可以在创建时指定初始容量。 
当我们向优先级队列添加元素时，它的容量会自动增长。

PriorityQueue不是线程安全的，因此java提供了PriorityBlockingQueue类，
它在java多线程环境中实现了BlockingQueue接口。

Java Priority Queue实现为enqueing和dequeing方法提供了O（log（n））时间。

我们偶尔需要按特定顺序处理队列中的项目。优先级队列是完成工作的数据结构。 
Java优先级队列与“普通”队列不同。它不是“先入先出”，而是按优先级顺序检索项目。

优先级队列Java
java.util.PriorityQueue类通过在内部使用优先级堆实现为我们提供了这种数据类
型的实现。 Java PriorityQueue是一个无界的队列。它是在Java 1.5中引入的，
并在Java SE 8版本中得到了增强。 PriorityQueue通过遵循“优先级堆”数据结构在内部实现。

Java PriorityQueue构造函数:

PriorityQueue（） - 使用默认初始容量创建PriorityQueue
PriorityQueue（Collection c） - 使用指定集合中的元素创建PriorityQueue
PriorityQueue（int initialCapacity） - 创建具有指定初始容量的PriorityQueue
PriorityQueue（int initialCapacity，Comparator comparator） - 使用提供的初始容量创建PriorityQueue，其元素的排序取决于指定的比较器
PriorityQueue（PriorityQueue c） - 创建包含指定优先级队列中元素的PriorityQueue
PriorityQueue（SortedSet c） - 创建包含指定有序集合中元素的PriorityQueue

优先级队列元素按其自然顺序排序，除非我们在创建时提供比较器。
默认情况下，元素按升序排序，因此队列的头部是优先级最低的元素。

如果有两个元素同时有资格成为头，那么这种关系是任意的。


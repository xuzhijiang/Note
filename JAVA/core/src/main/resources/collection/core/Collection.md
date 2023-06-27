# 容器为什么会出现?

如果要存储一堆对象,其实最简单的首先我们能想到的是使用数组,但是数组存储对象有如下弊端：

1. 数组长度一旦初始化后,存储对象的容量就不能改变
2. 获取数组中真实存储的对象的个数也没有现成方法可用.只能获取数组的总长度.

>所以基于数组的缺陷,才出现了容器.(严格说,数组也是一种容器)

# 容器

Java中的容器包括 Collection 和 Map 两类，Collection 存储着对象的集合，而 Map 存储着键值对（两个对象）的映射表。这里主要讨论Collection.

>当然也可以换个角度分,把容器分为线程不安全的容器和并发容器(多线程安全的).

![](Java集合框架类图.png)

![](Java集合概述.png)

# Collection(集合)

![](Collection类图01.png)

![](Collection类图02.png)

![](Collection_interfaces.png)

`java.util.Collection`位于集合框架顶部的接口.不提供直接的实现，而是由子接口/子类来实现,以下是其重要子接口:

* `java.util.Set`
* `java.util.List`
* `java.util.Queue`

![](HashSet类图01.png)

![](Set实现类类图.jpg)

![](Java_collection_list_implementations.jpg)

    注意jdk8中新特性: 接口中可以使用default关键字来实现一个方法.

# Collection重要的实现类(都是3大子接口的实现类)

* ArrayList(线程不安全的) - Object数组
* Vector(线程安全,因为内部的方法都使用了synchronized修饰) - Object数组
* LinkedList(线程不安全的) - 双向链表(JDK1.6之前为循环链表，JDK1.7取消了循环)
* CopyOnWriteArrayList(线程安全)

---

* HashMap(线程不安全的) - JDK1.8之前HashMap由数组+链表组成的，数组是主体，链表则是为了解决哈希冲突.JDK1.8以后当链表长度大于阈值（默认为8）时，将链表转化为红黑树，以减少搜索时间
* TreeMap(线程不安全的,红黑树,自平衡的二叉查找树)
* ConcurrentHashMap(线程安全)
* LinkedHashMap(线程不安全): 继承自 HashMap，底层仍然是由数组和链表或红黑树组成。另外，LinkedHashMap 在上面结构的基础上，增加了一条双向链表，使得上面的结构可以保持键值对的插入顺序。同时通过对链表进行相应的操作，实现了访问顺序相关逻辑

---

* HashSet(线程不安全的,无序，唯一,基于 HashMap 实现的)
- LinkedHashSet(继承与 HashSet，并且其内部是通过 LinkedHashMap 来实现的)
* TreeSet(线程不安全的,有序，唯一)
* CopyOnWriteArraySet(线程安全)

![](Vector.png)

---

* ArrayBlockingQueue(多线程安全,使用ReentrantLock)-数组有界队列
* LinkedBlockingQueue(多线程安全,使用ReentrantLock)- 链表有界队列
* PriorityBlockingQueue(优先级排序无界队列-多线程安全-使用ReentrantLock)
* DelayQueue(延时无界队列-多线程安全-使用ReentrantLock)
* ConcurrentLinkedQueue(无界线程安全基于链表的队列-使用cas实现线程安全)

## `java.util.Collection`接口中的方法

![](Collection接口中定义的方法.png)

# Collection遍历

Collection 继承了 Iterable 接口，其中Iterable接口定义的 iterator() 方法能够产生一个 Iterator 对象，通过这个对象就可以迭代遍历 Collection 中的元素.(迭代器模式)

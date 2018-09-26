Java Collections Framework consists of following parts:(Java集合框架包含下述部分)

Interfaces: Java Collections Framework interfaces provides the abstract data 
type to represent collection. java.util.Collection is the root interface of 
Collections Framework. It is on the top of Collections framework hierarchy. 
It contains some important methods such as size(), iterator(), add(), remove(), clear() 
that every Collection class must implement. Some other important interfaces are 
java.util.List, java.util.Set, java.util.Queue and java.util.Map. Map is the only 
interface that doesn’t inherits from Collection interface but it’s part of Collections 
framework. All the collections framework interfaces are present in java.util package.

接口: Java集合框架提供了表示集合的抽象数据类型，java.util.Collection是集合框架的根接口，它位于Collections框架层次的
顶部，它包含了一些像size(),iterator(),add(),remove(),clear()等每一个集合类必须要实现的重要方法。
一些其他的重要接口是java.util.List, java.util.Set, java.util.Queue, java.util.Map, Map是唯一的不从
Collection继承的接口，但是他是Collections框架的一部分，所有的集合框架接口都存在于java.util包中。

Implementation Classes: Collections in Java provides core implementation classes for 
collections. We can use them to create different types of collections in java program. 
Some important collection classes are ArrayList, LinkedList, HashMap, TreeMap, HashSet, 
TreeSet.These classes solve most of our programming needs but if we need some special 
collection class, we can extend them to create our custom collection class.

实现类: 一些重要的集合类是ArrayList, LinkedList, HashMap, TreeMap, HashSet, TreeSet.
这些类解决了我们大部分的编程需要，但是如果我们需要一些特殊集合类，我们可以扩展他们去创建我们自定义的集合类

Java 1.5 came up with thread-safe collection classes that allowed to modify Collections 
while iterating over it, some of them are CopyOnWriteArrayList, ConcurrentHashMap, 
CopyOnWriteArraySet. These classes are in java.util.concurrent package. All the collection 
classes are present in java.util and java.util.concurrent package.

Java1.5提出了线程安全的集合类，允许当迭代它的时候修改集合，他们中的一些是CopyOnWriteArrayList, ConcurrentHashMap, 
CopyOnWriteArraySet, 这些类是在java.util.concurrent保重，所有的这些集合类都存在于java.util和java.util.concurrent包中.

Algorithms: Algorithms are useful methods to provide some common functionalities, 
for example searching, sorting and shuffling.

算法：算法是提供一些常用功能的有用方法，例如搜索，排序和混洗。


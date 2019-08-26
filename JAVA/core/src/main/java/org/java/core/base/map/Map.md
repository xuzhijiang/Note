# 容器

Java中的容器包括 Collection 和 Map 两类，Collection 存储着对象的集合，而 Map 存储着键值对（两个对象）的映射表。这里主要讨论Map.

## Map

![](Map类图01.png)

![此图的HashTable继承关系有误](Map类图02.png)

>Map这种集合存储的元素是Entry(条目),一个Entry内部存储的是键值对映射,一个元素被称为一个Entry(条目)，
 Map提供了三个集合视图: 键集，键值映射集(entry) 和 值集合

>基于哈希表实现,key不能重复

## 常用实现类

- HashMap：基于哈希表实现的散列结构,使用拉链法(1.8达到阈值后使用红黑树解决冲突)解决冲突,底层采用数组+链表实现(线程不安全)
- HashTable：和 HashMap 类似(内部也是数组+链表)，只不过它是线程安全的.它是遗留类,比较老了，不应该再去使用它,jdk早就不更新了,现在可以使用 ConcurrentHashMap 来支持线程安全，并且 ConcurrentHashMap 的效率会更高，因为 ConcurrentHashMap 引入了分段锁.
- TreeMap：有序散列表，底层通过红黑树实现。
- LinkedHashMap：使用双向链表来维护元素的顺序，顺序为插入顺序或者最近最少使用（LRU）顺序。

![](AbstractMap类图.png)

![](HashMap类图.png)

![](HashTable类图.png)

![](TreeMap类图.png)

### HashMap为什么会出现

因为数组这种数据结构，虽然遍历简单，但是插入和删除操作复杂，需要移动数组内部的元素；链表这种数据结构，插入和删除操作简单，但是查找复杂，只能一个一个地遍历。

有没有一种新的数据结构，插入数据简单，同时查找也简单？ 这个时候就出现了哈希表这种数据结构。 这是一种折中的方式，插入没链表快，查询没数组快。

### 哈希表的定义

wiki上就是这么定义哈希表的：

散列表（Hash table，也叫哈希表），是根据关键字（Key value）而直接访问在内存存储位置的数据结构。也就是说，它通过计算一个关于键值的函数，将所需查询的数据映射到表中一个位置来访问记录，这加快了查找速度。这个映射函数称做散列函数，存放记录的数组称做散列表。

有几个概念要解释一下：

如果有1个关键字为k，它是通过一种函数f(k)得到散列表的地址，然后把值放到这个地址上。这个函数f就称为散列函数，也叫哈希函数。
对于不同的关键字，得到了同一地址，即k1 != k2，但是f(k1) = f(k2)。这种现象称为冲突，
若对于关键字集合中的任一个关键字，经散列函数映象到地址集合中任何一个地址的概率是相等的，则称此类散列函数为均匀散列函数
散列函数有好几种实现，分别有直接定址法、随机数法、除留余数法等，在wiki散列表上都有介绍。

散列表的冲突解决方法，也有好几种，有开放定址法、单独链表法、再散列等。

Java中的HashMap采用的冲突解决方法是使用单独链表法，如下图所示：
https://raw.githubusercontent.com/fangjian0423/blogimages/master/images/hashmap01.png

    
有2个重要的特性影响着HashMap的性能，分别是capacity(容量)和load factor(加载因子)。
<p>
其中capacity表示哈希表bucket的数量，HashMap的默认值是16。load factor加载因子表示
当一个map的实际大小达到了bucket的这个比例之后，和ArrayList一样，
将会创建原来HashMap大小的两倍的bucket数组，来重新调整map的大小，
并将原来的对象放入新的bucket数组中。这个过程也叫做再哈希。默认的load factor为0.75 。

### 特点

Java Map对象用于存储键值映射。 Map不能包含重复键.

5种实现Map接口并且经常使用的Map集合为:HashMap，Hashtable，TreeMap，ConcurrentHashMap和LinkedHashMap。
AbstractMap类提供了Map接口的骨架实现，大多数Map具体类扩展了AbstractMap类并实现了所需的方法。

Map提供了三个集合视图: 键集， 键值映射集和 值集合。

Map不保证映射的顺序，但它取决于实现。例如，HashMap不保证映射的顺序，
但TreeMap就可以保证.

Map使用hashCode和equals方法来获取和放置操作。所以可变类不适合Map键。
如果hashCode或equals的值在put之后发生更改，则在get操作中将无法获得正确的值。
因为Map是根据key的hashCode来计算value的位置的，如果key的hashCode或equals值变化了，
将计算错误的位置.

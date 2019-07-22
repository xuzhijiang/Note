## Map

### 概念

>Map这种集合存储的元素是Entry(条目),一个Entry内部存储的是键值对映射,一个元素被称为一个Entry(条目)，
 Map提供了三个集合视图: 键集，键值映射集(entry) 和 值集合

>基于哈希表实现,key不能重复

Map接口的常用实现类:HashTable，HashMap

1. HashMap：是基于‘拉链法’实现的散列表，底层采用数组+链表实现，一般用于单线程
2. HashTable：基于‘拉链法’实现的散列表，一般用于多线程
3. TreeMap：有序散列表，底层通过红黑树实现。

### 类的继承结构

```java
// Map不从任何Collection继承.
public interface Map<K,V>{}
```

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

一些重要的Map方法:

	Set<K> keySet（）：返回Map中所有键的Set视图。此键集由Map支持，因此对Map的任何修改都将反映到键集，反之亦然。
	Collection <V> values（）：返回Map中所有值的集合视图。此集合由Map支持，因此Map中的任何更改都将反映到此值集合，反之亦然。
	Set<Map.Entry <K，V >> entrySet（）：返回Map中映射的Set视图。此Set由Map支持，因此Map中的任何修改都将反映在条目集中，反之亦然。

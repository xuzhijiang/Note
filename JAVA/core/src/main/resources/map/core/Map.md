# 容器

Java中的容器包括 Collection 和 Map 两类，Collection 存储着对象的集合，而 Map 存储着键值对（两个对象）的映射表。这里主要讨论Map.

# Map为什么会出现

因为List这种数据结构，获取元素简单,可以直接通过索引下标，但是插入和删除操作复杂，需要移动内部的元素；链表这种数据结构，插入和删除操作简单，但是查找麻烦，只能一个一个地遍历。

有没有一种新的数据结构，插入数据简单，同时查找也简单？ 这个时候就出现了哈希表这种数据结构。 这是一种折中的方式，插入没链表快，查询没数组快。

# Map

![](../pics/Map类图.png)

![](../pics/Map类图01.png)

![HashTable继承关系有误,因为HashTabke不是继承AbstractMap,比较特殊](../pics/Map类图02.png)

# Map特点

Map这种容器存储的是键值对的映射,一个键值对被称为一个Entry(条目).

Map提供了三个集合视图: 键集，键值映射集(entry) 和 值集合

    Map不保证键(key)的顺序，它取决于实现。比如,HashMap是无序的，TreeMap 和 LinkedHashMap是有序的.

# 常用实现类

- HashMap：基于哈希表实现的散列结构,使用拉链法(1.8达到阈值后使用红黑树解决冲突)解决冲突,底层采用数组+链表实现(线程不安全)
- TreeMap：有序的Map，底层通过红黑树实现。(注意不是基于哈希表)
- LinkedHashMap：使用双向链表来维护元素的顺序，顺序为插入顺序或者最近最少使用（LRU）顺序。
- ConcurrentHashMap: 线程安全的HashMap.

![](../pics/AbstractMap类图.png)

![](../pics/HashMap类图.png)

![](../pics/HashTable类图.png)

![](../pics/TreeMap类图.png)

>AbstractMap类提供了Map接口的骨架实现，大多数Map具体类扩展了AbstractMap类并实现了所需的方法

# TreeMap和LinkedHashMap哪个有序实现比较好？(面试题)

TreeMap是可以按照我们的意愿来对键值对排序(比如学生的score),因为我们可以提供比较器,LinkedHashMap只能是按照插入顺序或者是访问顺序来遍历,比较适合于用作LRU.要区别二者.

# HashMap 与 HashTable 区别

- 默认容量不同,一个是11,一个是16
- HashTable线程安全,因为synchronized.hashmap不是线程安全的.
- HashTable 要慢，因为加锁
- hashtable也是数组 + 链表方式存储,它是遗留类,比较老了，不应该再去使用它

# 可以使用 CocurrentHashMap 来代替 Hashtable 吗？

Hashtable 是 synchronized 的，但是ConcurrentHashMap 同步性能更好，因为它使用了分段锁(1.7),锁的粒度更小.支持并发性更好.


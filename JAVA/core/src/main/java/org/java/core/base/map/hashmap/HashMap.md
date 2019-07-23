### HashMap特点

> HashMap extends了AbstractMap类,AbstractMap实现了Map interface.
根据hash算法来计算entry(key-value)的存储位置.一个key-value对，就是一个Entry,所有的Entry是用Set存放，不可重复.
HashMap中key用Set来存放，key不允许重复，value是用Collection存放，可重复.

* HashMap中的key和value都可以为null，且它的方法都没有synchronized。 
* HashMap几乎与Hashtable类似，只是HashMap不是同步的，并HashMap允许null键和值。
* HashMap不是有序集合
* 如果key用自定义的类，那么自定义的类就必须重写equals方法和hashCode方法
* 如果添加两个相同的key,后面的会替换前面的
* HashMap 判断两个 key 相等的标准是：两个 key 通过 equals() 方法返回 true，hashCode 值也相等。
* HashMap 判断两个 value相等的标准是：两个 value 通过 equals() 方法返回 true。
* HashMap的容量就是底层table数组的长度。
* 负载因子越大代表空间利用充分，但是查询，搜索效率就降低，过小，空间利用率就降低，
* HashMap是数组和链表的结合体
* HashMap是线程不安全的，采用Fail-Fast机制。
* HashMap进行数组扩容时，需要重新计算每个元素在数组中的位置，耗性能。

>HashMap对get和put操作使用了Key的hashCode()和equals()方法。所以HashMap关键对象应该提供这些方法的良好实现。
这就是不可变类更适合键的原因，例如String和Interger。

Java HashMap不是线程安全的，对于多线程环境，您应该使用ConcurrentHashMap类或使用
Collections.synchronizedMap（）方法获取同步映射。

How HashMap works in java?

java中的HashMap使用它的inner class Node<K，V>来存储映射mappings,HashMap使用散列算法，并在key上使用hashCode（）和equals（）方法进行get和put操作。

HashMap使用单链表来存储元素，这些元素称为bin或buckets。当我们调用put方法时，
key的hashCode用于确定存储映射(storing the mappings)的存储区(buckets)。

一旦识别出桶(buckets or 存储区)，hashCode就用于检查是否已存在具有相同hashCode的key。
如果存在具有相同hashCode的现有key，则在key上使用equals（）方法。
如果equals返回true，则覆盖value，
否则将对此单链接列表桶(bucket)进行新映射。如果没有具有相同hashCode的key，则将映射插入到桶中。

对于HashMap get操作，再次使用key的hashCode来确定要查找值的存储桶(存储区)(bucket)。
识别出存储桶后，遍历条目以使用hashCode和equals方法找出Entry。如果找到匹配，则返回值，否则返回null。

对于我们的工作，只需记住HashMap在Key上操作，并且需要良好的hashCode实现和equals方法以避免不必要的行为。


## HashMap底层实现原理

HashMap底层是一个数组，数据中的每一个元素就是Entry类型，Entry又是一个链表结构，在jdk1.7和1.8的实现上有所不同。

在jdk1.8之前,HashMap由数组 + 链表组成，也就是链表散列，数组是HashMap的主体，
链表实则是为了解决哈希冲突而存在的，（拉链法解决哈希冲突） 。

HashMap通过key的hashCode经过处理过后得到当前元素在数组中的存放位置(通过扰动函数实现，可以减少碰撞)，
如果当前位置存在元素的话，就判断该元素与要存入的元素的 hash 值以及 key 是否相同，
如果相同的话，直接覆盖，不相同就通过拉链法解决冲突。

所谓 “拉链法” 就是：将链表和数组相结合。也就是说创建一个数组，数组中每一格就是一个链表。
若遇到哈希冲突，则将冲突的值加到链表中即可。

从上面的源码可以看出，当我们向HashMap中添加一个元素时，先根据key的hashCode计算hash值，
根据这个hash值得到这个元素在数组的下标位置，如果这个位置已经存放了其他元素，那么他就会以链表的方式存放，
新加入的放在链头，最先加入的放在链尾，如果数组上该位置没有元素，就直接放到该位置上。

### 小结：

当我们从HashMap中读取一个元素时，首先判断key是否为空，不为空就根据key计算hash值，根据hash值找到数组中对应位置的元素，
然后通过key的equals方法，在对应位置的链表中找到需要元素的值。

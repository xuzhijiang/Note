# ConcurrentHashMap

* ConcurrentHashMap是map接口的实现类，ConcurrentHashMap是HashMap的线程安全版本。
* 线程安全的有ConcurrentHashMap，ConcurrentSkipListMap，HashTable，Properties(extends HashTable)，
但是HashTable是过时的类库，所以在并发中使用最多的是是ConcurrentHashMap和ConcurrentSkipListMap。
* ConcurrentHashMap在并发的开发中使用频率高，他的主要方法和HashMap基本一样。

## 1.7的ConcurrentHashMap实现原理

![](ConcurrentHashMapJDK7.jpg)

* 一个Segment中包含一个HashEntry数组
* 当我们要对HashEntry中的数据进行修改时，首先要获得是哪个Segment，然后获得这个Segment中的HashEntry数组，再根据HashEntry数组找到对应的HashEntry.

如图所示，1.7的ConcurrentHashMap是由 `Segment` 数组、`HashEntry` 数组组成，和 `HashMap` 一样，仍然是数组加链表组成。

>`ConcurrentHashMap` 采用了分段锁技术，其中 `Segment` 继承于 `ReentrantLock`。不会像 `HashTable` 那样不管是 `put` 还是 `get` 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 `CurrencyLevel` (Segment 数组数量)的线程并发。每当一个线程占用锁访问一个 `Segment` 时，不会影响到其他的 `Segment`。

### get 方法
`ConcurrentHashMap` 的 `get` 方法是非常高效的，因为整个过程都不需要加锁。

只需要将 `Key` 通过 `Hash` 之后定位到具体的 `Segment` ，再通过一次 `Hash` 定位到具体的元素上。由于 `HashEntry` 中的 `value` 属性是用 `volatile` 关键词修饰的，保证了内存可见性，所以每次获取时都是最新值

### put 方法

虽然 HashEntry 中的 value 是用 `volatile` 关键词修饰的，但是并不能保证并发的原子性，所以 put 操作时仍然需要加锁处理。

首先也是通过 Key 的 Hash 定位到具体的 Segment，在 put 之前会进行一次扩容校验。这里比 HashMap 要好的一点是：HashMap 是插入元素之后再看是否需要扩容，有可能扩容之后后续就没有插入就浪费了本次扩容(扩容非常消耗性能)。

而 ConcurrentHashMap 不一样，它是在将数据插入之前检查是否需要扩容，之后再做插入操作。

### size 方法

每个 `Segment` 都有一个 `volatile` 修饰的全局变量 `count` ,求整个 `ConcurrentHashMap` 的 `size` 时很明显就是将所有的 `count` 累加即可。但是 `volatile` 修饰的变量却不能保证多线程的原子性，所有直接累加很容易出现并发问题。

但如果每次调用 `size` 方法将其余的修改操作加锁效率也很低。所以做法是先尝试两次将 `count` 累加，如果容器的 `count` 发生了变化再加锁来统计 `size`。

至于 `ConcurrentHashMap` 是如何知道在统计时大小发生了变化呢，每个 `Segment` 都有一个 `modCount` 变量，每当进行一次 `put remove` 等操作，`modCount` 将会 +1。只要 `modCount` 发生了变化就认为大小在发生变化。

## JDK1.8 实现

1.8 中的 ConcurrentHashMap 数据结构和实现与 1.7 还是有着明显的差异。

其中抛弃了原有的 Segment 分段锁，而采用了 `CAS + synchronized` 来保证并发安全性。

也将 1.7 中存放数据的 HashEntry 改为 Node，但作用都是相同的。其中的 `val next` 都用了 volatile 修饰，保证了可见性。

### put 方法

重点来看看 put 函数：

![](https://ws3.sinaimg.cn/large/006tNc79gy1fthrz8jlo8j30oc0rbte3.jpg)

- 根据 key 计算出 hashcode 。
- 判断是否需要进行初始化。
- `f` 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
- 如果当前位置的 `hashcode == MOVED == -1`,则需要进行扩容。
- 如果都不满足，则利用 synchronized 锁写入数据。
- 如果数量大于 `TREEIFY_THRESHOLD` 则要转换为红黑树。

### get 方法

![](https://ws1.sinaimg.cn/large/006tNc79gy1fthsnp2f35j30o409hwg7.jpg)

- 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
- 如果是红黑树那就按照树的方式获取值。
- 都不满足那就按照链表的方式遍历获取值。

## 总结

1.8 在 1.7 的数据结构上做了大的改动，采用红黑树之后可以保证查询效率（`O(logn)`），甚至取消了 ReentrantLock 改为了 synchronized，这样可以看出在新版的 JDK 中对 synchronized 优化是很到位的。

##源码

```java
// ConcurrentHashMap继承了AbstractMap这个类
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V>
    implements ConcurrentMap<K, V>, Serializable {
```

>ConcurrentHashMap完全允许多个读操作并发进行，读操作并不需要加锁初始化segments数组

```java
    public ConcurrentHashMap(int initialCapacity,
                            float loadFactor, int concurrencyLevel) {
}
```

* 构造方法中initialCapacity代表数组容量，loadFactor代表负载因子，concurrencyLevel代表并发级别。
* segment数组的长度通过concurrencyLevel计算得出，为了通过按位与的哈希算法类定位segment的索引，就必须保证segment数组的长度是2的N次方，所以必须计算出一个大于或等于concurrencyLevel的最小的2的n次方值来作为segment数组的长度，concurrencyLevel的最大并发级别是655535，所以segment数组的最大长度为66636.对应的二进制是16位。

## 常见问题

看完了整个 HashMap 和 ConcurrentHashMap 在 1.7 和 1.8 中不同的实现方式相信大家对他们的理解应该会更加到位。

其实这块也是面试的重点内容，通常的套路是：

1. 谈谈你理解的 HashMap，讲讲其中的 get put 过程。
2. 1.8 做了什么优化？
3. 是线程安全的嘛？
4. 不安全会导致哪些问题？
5. 如何解决？有没有线程安全的并发容器？
6. ConcurrentHashMap 是如何实现的？ 1.7、1.8 实现有何不同？为什么这么做？
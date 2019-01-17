Some of the important points to remember about TreeMap in java are:

除了实现(Apart from implementing Map interface)Map接口之外，
Java TreeMap还实现了NavigableMap并间接实现了SortedMap接口。 
TreeMap还extends了AbstractMap类。

public class TreeMap<K,V> extends AbstractMap<K,V>
implements NavigableMap<K,V>, Cloneable, java.io.Serializable{}

TreeMap条目按其key的自然顺序排序。 它还提供了一个构造函数，用于提供用于排序的Comparator。
 因此，如果您使用任何类作为键，请确保它实现Comparable接口以进行自然排序。

Java TreeMap implementation对于containsKey，get，put和remove操作保证了他们
的log(n)的时间耗费。

TreeMap不同步，因此不是线程安全的。 对于多线程环境，
您可以使用Collections.synchronizedSortedMap方法获取包装同步。

TreeMap methods to get keyset and values return Iterator that 
are fail-fast in nature, so any concurrent modification will 
throw ConcurrentModificationException.
get keyset and values的TreeMap方法返回Iterator本质上是故障快速的，
因此任何并发修改都会抛出ConcurrentModificationException。

TreeMap in java doesn’t allow null keys, however you 
can have multiple null values associated with different keys.
java中的TreeMap不允许使用null键，但是您可以将多个null值与不同的键相关联。

TreeMap vs HashMap:

1. TreeMap和HashMap都实现了Map接口.
2. TreeMap条目按键的自然顺序排序，而HashMap不按任何顺序存储条目。
3. TreeMap不允许使用null键，而我们可以在HashMap中使用一个空键。
4. 由于TreeMap以排序方式存储条目，因此它在存储和检索对象时比HashMap慢一点。
4. TreeMap使用基于Red-Black树的NavigableMap实现，而HashMap使用散列算法实现。
5. TreeMap实现了NavigableMap，因此您可以获得HashMap中不存在的一些额外功能。

何时在Java中使用TreeMap?

大多数情况下，HashMap足以在程序中用作Map实现。 但是如果你有一些与排序相关的特殊要求，
那么你可以使用TreeMap。
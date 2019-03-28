Java HashMap是java中最受欢迎的Collection类之一。 
Java HashMap是基于Hash table的实现。 Java中的HashMap extends了AbstractMap类,
AbstractMap实现了Map interface.

Java中HashMap的一些重要观点是：

Java HashMap允许null键和null值。
HashMap不是有序集合。您可以通过keys set迭代HashMap条目(entries)，
但不保证它们按照它们添加到HashMap的顺序。
HashMap几乎与Hashtable类似，只是它不同步并允许null键和值。
HashMap使用它的inner class Node<K,V>来存储map entries.

HashMap stores entries into multiple singly linked lists, 
called buckets or bins. Default number of bins 
is 16 and it’s always power of 2.

HashMap对get和put操作的键使用hashCode（）和equals（）方法。
所以HashMap关键对象应该提供这些方法的良好实现。这就是不可变类更适合键的原因，
例如String和Interger。

Java HashMap不是线程安全的，对于多线程环境，
您应该使用ConcurrentHashMap类或使用
Collections.synchronizedMap（）方法获取同步映射。

Java HashMap提供了四个构造函数：

public HashMap（）：最常用的HashMap构造函数。 
此构造函数将创建一个空的HashMap，其默认初始容量为16，加载因子为0.75。

public HashMap（int initialCapacity）：此HashMap构造函数用于指定初始容量和0.75加载因子。 
如果您知道要存储在HashMap中的映射数，这对于避免重新散列非常有用。

public HashMap（int initialCapacity，float loadFactor）：这个HashMap构造函数将创建一个具
有指定初始容量和加载因子的空HashMap。 如果知道要在HashMap中存储的最大映射数，则可以使用此方法。 
在常见情况下，您应该避免这种情况，因为负载系数0.75可以在空间和时间成本之间进行良好的权衡。

public HashMap（Map <？extends K，？extends V> m）：创建一个映射，其映射与指定的映射相同，加载因子为0.75

java中HashMap的重要方法：

public void clear（）：这个HashMap方法将删除所有映射，HashMap将变为空。
public boolean containsKey（Object key）：如果密钥存在，则此方法返回'true'，否则返回'false'。
public boolean containsValue（Object value）：如果值存在，则此HashMap方法返回true，否则返回false。
public Set <Map.Entry <K，V >> entrySet（）：此方法返回HashMap映射的Set视图。此set由map支持，因此对map的更改将反映在set中，反之亦然。
public V get（Object key）：返回映射到指定键的值，如果没有键的映射，则返回null。
public boolean isEmpty（）：如果不存在键值映射，则返回true的实用程序方法。
public Set <K> keySet（）：返回此映射中包含的键的Set视图。该集由地图支持，因此对地图的更改将反映在集中，反之亦然。
public V put（K key，V value）：将指定值与此映射中的指定键相关联。如果映射先前包含键的映射，则替换旧值。
public void putAll（Map <？extends K，？extends V> m）：将指定地图中的所有映射复制到此地图。这些映射将替换此映射对当前位于指定映射中的任何键的任何映射。
public V remove（Object key）：从此映射中移除指定键的映射（如果存在）。
public int size（）：返回此映射中键 - 值映射的数量。
public Collection <V> values（）：返回此映射中包含的值的Collection视图。该集合由地图支持，因此对地图的更改将反映在集合中，反之亦然。


There are many new methods in HashMap introduced in Java 8：

public V computeIfAbsent（K key，Function <？super K，？extends V> mappingFunction）：如果指定的键尚未与值关联（或映射为null），则此方法尝试使用给定的映射计算其值函数并将其输入HashMap，除非Null。
public V computeIfPresent（K key，BiFunction <？super K，？super V，？extends V> remappingFunction）：如果指定键的值存在且为非null，则尝试计算给定键及其当前键的新映射映射值。
public V compute（K key，BiFunction <？super K，？super V，？extends V> remappingFunction）：此HashMap方法尝试计算指定键及其当前映射值的映射。
public void forEach（BiConsumer <？super K，？super V> action）：此方法为此映射中的每个条目执行给定操作。
public V getOrDefault（Object key，V defaultValue）：与get相同，但如果未找到指定键的映射，则返回defaultValue。
public V merge（K key，V value，BiFunction <？super V，？super V，？extends V> remappingFunction）：如果指定的键尚未与值关联或与null关联，则将其与给定的non关联 - 空值。否则，将相关值替换为给定重映射函数的结果，或者如果结果为null则删除。
public V putIfAbsent（K key，V value）：如果指定的键尚未与值相关联（或映射为null），则将其与给定值相关联并返回null，否则返回当前值。
public boolean remove（Object key，Object value）：仅当指定键当前映射到指定值时才删除该条目。
public boolean replace（K key，V oldValue，V newValue）：仅当前映射到指定值时，替换指定键的条目。
public V replace（K key，V value）：仅当指定键当前映射到某个值时，才替换该键。
public void replaceAll（BiFunction <？super K，？super V，？extends V> function）：将每个条目的值替换为在该条目上调用给定函数的结果。

How HashMap works in java?

java中的HashMap使用它的inner class Node<K，V>来存储映射mappings.
HashMap使用散列算法，并在key上使用hashCode（）和equals（）方法进行get和put操作。

HashMap使用单链表来存储元素，这些元素称为bin或buckets。当我们调用put方法时，
key的hashCode用于确定存储映射(storing the mappings)的存储区(buckets)。

一旦识别出桶(buckets or 存储区)，hashCode就用于检查是否已存在具有相同hashCode的key。
如果存在具有相同hashCode的现有key，则在key上使用equals（）方法。
如果equals返回true，则覆盖value，
否则将对此单链接列表桶(bucket)进行新映射。如果没有具有相同hashCode的key，则将映射插入到桶中。

对于HashMap get操作，再次使用key的hashCode来确定要查找值的存储桶(存储区)(bucket)。
识别出存储桶后，遍历条目以使用hashCode和equals方法找出Entry。如果找到匹配，则返回值，否则返回null。

还有更多的事情涉及到诸如散列算法以获取密钥，重新映射映射等等。但是对于我们的工作，
只需记住HashMap操作在Key上工作，并且需要良好的hashCode实现和equals方法以避免不必要的行为。

默认load factor 0.75提供了空间和时间复杂度之间的良好折衷。
但您可以根据需要将其设置为不同的值。如果你想节省空间，那么你可以将它的值增加到0.80或0.90，
但是获取/放置操作将花费更多时间。

Load Factor is used to figure out when HashMap will be rehashed and bucket size will be increased. 
Default value of bucket or capacity is 16 and load factor is 0.75. 



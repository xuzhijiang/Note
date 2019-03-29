## Map概述

>基于哈希表实现

Map接口的常用实现类:HashTable，HashMap

### HashMap特点

> HashMap extends了AbstractMap类,AbstractMap实现了Map interface.
根据hash算法来计算entry(key-value)的存储位置.一个key-value对，就是一个Entry,所有的Entry是用Set存放，不可重复.
HashMap中key用Set来存放，key不允许重复，value是用Collection存放，可重复.

* 允许使用null值和null键,非同步
* 不保证映射的顺序,HashMap不是有序集合。您可以通过keys set迭代HashMap条目(entries)，
但不保证它们按照它们添加到HashMap的顺序。
* HashMap几乎与Hashtable类似，只是它不同步并允许null键和值。
* HashMap使用它的inner class Node<K,V>来存储map entries.
* 如果key用自定义的类，那么自定义的类就必须重写equals方法和hashCode方法
* 如果添加两个相同的key,后面的会替换前面的
* HashMap 判断两个 key 相等的标准是：两个 key 通过 equals() 方法返回 true，hashCode 值也相等。
* HashMap 判断两个 value相等的标准是：两个 value 通过 equals() 方法返回 true。
* HashMap的容量就是底层table数组的长度。
* 负载因子：散列表的实际元素数目/散列表的容量，负载因子衡量的是一个散列表的空间使用程度，值越大代表装填程度越高，反之越小，如果负载因子越大代表空间利用充分，但是查询，搜索效率就降低，过小，空间利用率就降低，

HashMap stores entries into multiple singly linked lists, called buckets or bins. Default number of bins 
is 16 and it’s always power of 2.

>HashMap对get和put操作使用了Key的hashCode()和equals()方法。所以HashMap关键对象应该提供这些方法的良好实现。
这就是不可变类更适合键的原因，例如String和Interger。

Java HashMap不是线程安全的，对于多线程环境，您应该使用ConcurrentHashMap类或使用
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
public Set <Map.Entry <K，V >> entrySet（）：遍历HashMap的键值对
public V get（Object key）：返回映射到指定键的值，如果没有键的映射，则返回null。
public boolean isEmpty（）：如果不存在键值映射，则返回true的实用程序方法。
public Set <K> keySet（）：返回一个Set，包含了HashMap中的Key,用于遍历HashMap中的所有Key
public V put（K key，V value）：将指定值与此映射中的指定键相关联。如果映射先前包含键的映射，则替换旧值。
public void putAll（Map <？extends K，？extends V> m）：将指定地图中的所有映射复制到此地图。这些映射将替换此映射对当前位于指定映射中的任何键的任何映射。
public V remove（Object key）：从此映射中移除指定键的映射（如果存在）。
public int size（）：返回此映射中键 - 值映射的数量。
public Collection <V> values（）：返回一个 Collection，包含了HashMap中的value，遍历HashMap中的value


```java
public static void main(String[] args) {
	HashMap<Object,Object> hashMap = new HashMap<Object, Object>();
	hashMap.put("AA", new Person("张三",12));
	hashMap.put("BB", new Person("李四",13));
	hashMap.put("CC", new Person("王五",14));
	hashMap.put("DD", new Person("陈六",14));
	Collection<Object> values = hashMap.values();
	//values的遍历方式一
	for (Object object : values) {
		System.out.println(object);
	}
	//values的遍历方式二
	Iterator<Object> iterator = values.iterator();
	while(iterator.hasNext()){
		System.out.println(iterator.next());
	}
}
```

```java
public static void main(String[] args) {
	HashMap<Object,Object> hashMap = new HashMap<Object, Object>();
	hashMap.put("AA", new Person("张三",12));
	hashMap.put("BB", new Person("李四",13));
	hashMap.put("CC", new Person("王五",14));
	hashMap.put("DD", new Person("陈六",14));
	Set<Entry<Object,Object>> entrySet = hashMap.entrySet();
	for (Entry<Object, Object> entry : entrySet) {
		System.out.println(entry);
	}
}
```

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

## HashMap底层实现原理
在HashMap的源码中我们可以看出HashMap是一个数组和链表结合的一种数据结构，

### 1.HashMap属性

```java
transient Entry[] table;
static class Entry<K,V> implements Map.Entry<K,V> {
	final K key;
	V value;
	Entry<K,V> next;
	final int hash;
```

可以看出，HashMap底层是一个数组，数据中的每一个元素就是Entry类型，Entry又是一个链表结构。

### 3.添加数据方法
```java
	public V put(K key, V value) {
	//判断key是否为空
	if (key == null)
		return putForNullKey(value);
	//根据key的hashcode值来计算hash值。
	int hash = hash(key.hashCode());
	int i = indexFor(hash, table.length);
	//如果索引i出的Entry不为null，通过循环遍历e的下一个元素
	for (Entry<K,V> e = table[i]; e != null; e = e.next) {
		Object k;
		if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
			V oldValue = e.value;
			e.value = value;
			e.recordAccess(this);
			return oldValue;
		}
	}
	//如果索引i出的Entry为null,说明此处没有Entry
	modCount++;
	//将key-value放在次索引处
	addEntry(hash, key, value, i);
	return null;
}
	//如果key为空就将value放在数组的第一个位置
	private V putForNullKey(V value) {
	for (Entry<K,V> e = table[0]; e != null; e = e.next) {
		if (e.key == null) {
			V oldValue = e.value;
			e.value = value;
			e.recordAccess(this);
			return oldValue;
		}
	}
	modCount++;
	addEntry(0, null, value, 0);
	return null;
}
//计算hashcode的
	public final int hashCode() {
		return (key==null   ? 0 : key.hashCode()) ^
				(value==null ? 0 : value.hashCode());
	}
//计算hash值的
static int hash(int h) {
	h ^= (h >>> 20) ^ (h >>> 12);
	return h ^ (h >>> 7) ^ (h >>> 4);
}
//搜索指定Hash在对应table中的索引
	static int indexFor(int h, int length) {
	return h & (length-1);
}
//根据hash值，将key-value放在数组table的索引i位置上。
void addEntry(int hash, K key, V value, int bucketIndex) {
	//获取指定bucketIndex索引处的Entry
	Entry<K,V> e = table[bucketIndex];
	//将新创建的Entry放入bucketIndex索引位置，并让新的Entry指向原来的Entry。
	table[bucketIndex] = new Entry<>(hash, key, value, e);
	//如果数组长度不够，就扩容
	if (size++ >= threshold)
	//将table数组的长度扩展到原来的2倍
	resize(2 * table.length);
}
```
#### 说明：
> &是与运算符，将int转为二进制，只有两个位都是1，结果才是1。
#### 小结：
从上面的源码可以看出，当我们向HashMap中添加一个元素时，先根据key的hashCode重新计算hash值，根据这个hash值得到这个元素在数组中的位置，也就是数组的下标，如果这个位置已经存放了其他元素，那么他就会以链表的方式存放，新加入的放在链头，最先加入的放在链尾，如果数组上该位置没有元素，就直接放到该位置上。
### 4.读取数据
```java
public V get(Object key) {
if (key == null)
	return getForNullKey();
int hash = hash(key.hashCode());
for (Entry<K,V> e = table[indexFor(hash, table.length)];
		e != null;
		e = e.next) {
	Object k;
	if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
		return e.value;
}
return null;
}
```
#### 小结：
当我们从HashMap中读取一个元素时，首先判断key是否为空，不为空就根据key计算hash值，根据hash值找到数组中对应位置的元素，然后通过key的equals方法，在对应位置的链表中找到需要元素的值。
##HashMap 总结
* HashMap是数组和链表的结合体，允许插入null的key和value。
* HashMap是线程不安全的，采用Fail-Fast机制。
* HashMap进行数组扩容时，需要重新计算每个元素在数组中的位置，耗性能。

## ConcurrentHashMap基本知识
### 概述
* ConcurrentHashMap是map接口的实现类，因为HashMap是线程不安全的，因此ConcurrentHashMap可以看成是HashMap的线程安全版本。
* 线程安全的有ConcurrentHashMap，ConcurrentSkipListMap，HashTable，Properties，但是HashTable是过时的类库，所以在并发中使用最多的是是ConcurrentHashMap和ConcurrentSkipListMap。
* ConcurrentHashMap在并发的开发中使用频率高，所以他的主要方法和HashMap基本一样。
## ConcurrentHashMap实现原理
### 概述
* ConcurrentHashMap是由Segment数组和HashEntry数组组成，
* Segment的结构和HashMap差不多，是数组和链表结合的数组结构，Segment是一种可重入锁，在ConcurrentHaspMap中就是锁的角色，
* HashEntry是存放键值对数据，在源码中我们可以出一个Segment中包含一个HashEntry，
* 换句话说，一个Segment守护一个HashEntry，当我们要对HashEntry中的数据进行修改时，首先要获得HashEntry数据所对应的Segment锁。
### 源码分析如下
```java
public class ConcurrentHashMap<K, V> extends AbstractMap<K, V>
    implements ConcurrentMap<K, V>, Serializable {
```
* 源码中可以看出ConcurrentHashMap继承了AbstractMap这个类，实现了ConcurrentMap和Serializable这两个接口，说明这个类具备了Map基本的骨干方法。
### 1.构造方法
#### 1.ConcurrentHashMap() 
创建一个带有默认初始容量 (16)、加载因子 (0.75) 和 concurrencyLevel (16) 的新的空映射。
#### 2.ConcurrentHashMap(int initialCapacity)
创建一个带有指定初始容量、默认加载因子 (0.75) 和 concurrencyLevel (16) 的新的空映射。
#### 3.ConcurrentHashMap(int initialCapacity, float loadFactor)
创建一个带有指定初始容量、加载因子和默认 concurrencyLevel (16) 的新的空映射。
#### 4.ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel)
创建一个带有指定初始容量、加载因子和并发级别的新的空映射。
#### 5.ConcurrentHashMap(Map<? extends K,? extends V> m) 
构造一个与给定映射具有相同映射关系的新映射
### 2.属性
ConcurrentHashMap使用segments数组来保存锁，源码如下：
```java
	final Segment<K,V>[] segments;
```
ConcurrentHashMap完全允许多个读操作并发进行，读操作并不需要加锁，在ConcurrentHashMap中保证HashEntry几乎不可变，使用final关键字。源码如下：
```java
	static final class HashEntry<K,V> {
        final int hash;
        final K key;
        volatile V value;
        volatile HashEntry<K,V> next;
```
### 3.如何初始化segments数组
```java
    public ConcurrentHashMap(int initialCapacity,
                            float loadFactor, int concurrencyLevel) {
    if (!(loadFactor > 0) || initialCapacity < 0 || concurrencyLevel <= 0)
        throw new IllegalArgumentException();
    if (concurrencyLevel > MAX_SEGMENTS)
        concurrencyLevel = MAX_SEGMENTS;
    int sshift = 0;
    int ssize = 1;
    while (ssize < concurrencyLevel) {
        ++sshift;
        ssize <<= 1;
    }
    this.segmentShift = 32 - sshift;
    this.segmentMask = ssize - 1;
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    int c = initialCapacity / ssize;
    if (c * ssize < initialCapacity)
        ++c;
    int cap = MIN_SEGMENT_TABLE_CAPACITY;
    while (cap < c)
        cap <<= 1;
    Segment<K,V> s0 =
        new Segment<K,V>(loadFactor, (int)(cap * loadFactor),
                            (HashEntry<K,V>[])new HashEntry[cap]);
    Segment<K,V>[] ss = (Segment<K,V>[])new Segment[ssize];
    UNSAFE.putOrderedObject(ss, SBASE, s0);
    this.segments = ss;
}
```
说明：
* 构造方法中initialCapacity代表数组容量，loadFactor代表负载因子，concurrencyLevel代表并发级别。
* segment数组是ConcurrentHashMap保存锁的数组，segment数组的初始化由段偏移量segmentShift，段掩码segmentmask和HashEntry数组组成。
* segment数组的长度ssize通过concurrencyLevel计算得出，为了通过按位与的哈希算法类定位segment的索引，就必须保证segment数组的长度是2的N次方，所以必须计算出一个大于或等于concurrencyLevel的最小的2的n次方值来作为segment数组的长度，concurrencyLevel的最大并发级别是655535，所以segment数组的最大长度为66636.对应的二进制是16位。


### Java数据结构
Java中的数据结构主要分为Collection和map两个接口（接口只提供抽象方法，并不提供实现），程序中主要使用的是数据结构是这两个接口的主要实现类。

### List：有序的可重复的Collection

使用此接口能够精确的控制每一个元素插入的位置，可以通过索引来访问List中的元素。

* ArrayList采用数组实现，数组的访问速度要比链表快，所以ArrayList更适合查询操作，
* LinkedList采用链表实现，随机插入和删除的效率要高于数组，
* Vector是一种古老的实现类，采用数组的实现，内部方法使用了Sychronized关键字，是线程安全的。

### Map：‘键值’对映射的抽象接口，不包括重复的键。
1. HashMap：是基于‘拉链法’实现的散列表，底层采用数组+链表实现，一般用于单线程
2. HashTable：基于‘拉链法’实现的散列表，一般用于多线程
3. TreeMap：有序散列表，底层通过红黑树实现。

# 2018.06.15代码规范总结

* 控制层的方法体尽可能小，数据处理代码放在业务处理层
* 字符串使用StringUtils工具类判空
* 集合list使用CollectionUtils工具类进行判空
* 遍历Map使用entrySet，避免使用keySet
* 注意代码的缩进格式，是否进行的格式化
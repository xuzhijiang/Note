public interface Map<K,V>{}

Java Map是collections framework的一部分。 
Java Map对象用于存储键值映射。 Java Map不能包含重复键.

Java中流行的Map实现类是HashMap，Hashtable，TreeMap，ConcurrentHashMap和LinkedHashMap。
AbstractMap类提供了Map接口的骨架实现，大多数Map具体类扩展了AbstractMap类并实现了所需的方法。

Map提供了三个集合视图: 键集， 键值映射集和 值集合。

Map不保证映射的顺序，但它取决于实现。例如，HashMap不保证映射的顺序，
但TreeMap就可以保证.

Map使用hashCode和equals方法来获取和放置操作。所以可变类不适合Map键。
如果hashCode或equals的值在put之后发生更改，则在get操作中将无法获得正确的值。
因为Map是根据key的hashCode来计算value的位置的，如果key的hashCode或equals值变化了，
将计算错误的位置.

一些重要的Map方法:

	int size（）：返回此Map中键 - 值映射的数量。
	boolean isEmpty（）：如果没有映射，则返回true，否则返回false。
	boolean containsValue（Object value）：如果至少有一个键映射到指定值，则返回true，否则返回false。
	V get（Object key）：返回映射到给定键的值，如果没有找到映射则返回null。
	V put（K key，V value）：将键值对的映射添加到地图中。如果已存在映射到此键的值，则替换该值。此方法返回与key关联的先前值，如果没有key的映射，则返回null。
	V remove（Object key）：从此映射中删除键的映射（如果存在）。返回此映射先前与该键关联的值，如果映射不包含该键的映射，则返回null。
	void putAll（Map <？extends K，？extends V> m）：将指定映射中的所有映射复制到此映射。
	void clear（）：从Map中删除所有映射。
	Set<K> keySet（）：返回Map中所有键的Set视图。此键集由Map支持，因此对Map的任何修改都将反映到键集，反之亦然。
	Collection <V> values（）：返回Map中所有值的集合视图。此集合由Map支持，因此Map中的任何更改都将反映到此值集合，反之亦然。
	Set<Map.Entry <K，V >> entrySet（）：返回Map中映射的Set视图。此Set由Map支持，因此Map中的任何修改都将反映在条目集中，反之亦然。

相关：StringBuffer与StringBuilder

Java 8中引入的Java Map中的方法很少: 

default V getOrDefault(Object key, V defaultValue): ：返回指定键映射到的值，如果此映射不包含键的映射，则返回defaultValue。
default void forEach（BiConsumer <？super K，？super V> action）：对此映射中的每个条目执行给定操作。
default void replaceAll（BiFunction <？super K，？super V，？extends V> function）：将每个条目的值替换为在该条目上调用给定函数的结果。
default V putIfAbsent（K key，V value）：如果指定的键尚未与值关联（或映射为null），则将其与给定值关联并返回null，否则返回当前值。
default boolean remove（Object key，Object value）：仅当指定键当前映射到指定值时才删除该条目。
default boolean replace（K key，V oldValue，V newValue）：仅当前映射到指定值时，替换指定键的条目。
default V replace(K key, V value): 仅当指定键当前映射到某个值时，才替换该键。
default V computeIfAbsent（K key，Function <？super K，？extends V> mappingFunction）：如果指定的键尚未与值相关联（或映射为null），则尝试使用给定的映射函数计算其值除非为null，否则将其输入此映射。
default V computeIfPresent（K key，BiFunction <？super K，？super V，？extends V> remappingFunction）：如果指定键的值存在且为非null，则尝试计算给定键及其当前键的新映射映射值。如果函数返回null，则删除映射。
default V compute（K key，BiFunction <？super K，？super V，？extends V> remappingFunction）：尝试计算指定键及其当前映射值的映射（如果没有当前映射，则为null）。
default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction):如果指定的键尚未与值关联或与null关联，则将其与给定的非关联 - 空值。否则，将相关值替换为给定重映射函数的结果，或者如果结果为null则删除。

相关：Java BlockingQueue示例

您会注意到Java 8 Map接口中添加的所有新方法都是带有实现的默认方法。
这样做是为了确保实现Map interfac的任何类都不会发生编译错误
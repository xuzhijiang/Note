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

您会注意到Java 8 Map接口中添加的所有新方法都是带有实现的默认方法。
这样做是为了确保实现Map interfac的任何类都不会发生编译错误
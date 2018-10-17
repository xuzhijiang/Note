Java HashSet是Set接口最流行的实现。 java.util.HashSet由HashMap支持。 
HashSet extends AbstractSet类并实现了Set，Cloneable和Serializable接口。

public class HashSet<E> extends AbstractSet<E>
implements Set<E>, Cloneable, java.io.Serializable{}

Java中HashSet的一些重要观点是：

HashSet不允许重复条目。
HashSet允许null作为值。
HashSet不保证元素的插入顺序。
HashSet不是线程安全的。您可以使用Collections.synchronizedSet方法以性能为代价获取线程安全的HashSet。
您还可以使用CopyOnWriteArraySet并发类来实现线程安全。
HashSet迭代器方法是快速失败的。
因此，在创建迭代器之后对集合进行任何结构修改都会抛出ConcurrentModificationException。
HashSet支持泛型，这是在运行时避免ClassCastException的推荐方法。
HashSet使用HashMap存储元素，因此对象应该提供hashCode（）和equals（）
方法的良好实现，以避免不必要的结果。

Java HashSet提供了四个构造函数:
public HashSet(): 
public HashSet(int initialCapacity): 
public HashSet(int initialCapacity, float loadFactor): 
public HashSet(Collection<? extends E> c): 

Some of the useful HashSet methods ars:

public boolean add（E e）：如果尚未存在，则将给定元素添加到Set。此方法在内部使用equals（）方法检查重复项，因此请确保您的对象正确定义equals（）方法。
public void clear（）：从Set中删除所有元素。
public Object clone（）：返回Set实例的浅表副本。
public boolean contains（Object o）：如果Set包含给定元素，则返回true，othrweise false。
public boolean isEmpty（）：如果Set不包含元素，则返回true，否则返回false。
public Iterator <E> iterator（）：返回此set中元素的迭代器。元素按特定顺序返回。
public boolean remove（Object o）：从该集合中移除给定元素（如果存在）并返回true。如果元素不在集合中，则返回false。
public int size（）：返回集合中的元素数。
public Spliterator <E> spliterator（）：在此集合中的元素上创建一个后期绑定和失败快速的Spliterator。这是在Java 8中引入的，但是到目前为止我还没有使用它。
public boolean removeAll（Collection <？> c）：HashSet从AbstractSet继承此方法。此方法将删除集合中属于指定集合的​​所有元素。


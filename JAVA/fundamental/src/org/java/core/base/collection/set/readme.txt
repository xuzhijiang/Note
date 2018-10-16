Java Set是不包含重复元素的元素（or 对象）的集合。

 Java Set是一个扩展Collection接口的接口。 
 与List不同，Java Set不是有序集合，它的元素没有特定的顺序。 
 Java Set不提供对可插入元素的位置的控制。
  您不能通过索引访问元素，也不可以搜索列表中的元素。

有关Java Set的一些重要观点：

	1. Java Set接口是Java Collections Framework的成员。
	2. 与List不同，Set不允许添加重复元素。
	3. Set允许您最多只添加一个null元素。
	4. Set接口在Java 8中有一个默认方法：spliterator。
	5. 与List和数组不同，Set不支持它的元素的索引或位置。
	6. Set支持泛型，我们应尽可能使用它。  使用Generics with Set将在运行时避免ClassCastException。
	7. 我们可以使用Set接口实现来维护唯一元素。

	public interface Set<E> extends Collection<E>
	
	public interface Collection<E> extends Iterable<E>



1604/5000
Java Set接口扩展了Collection接口。Collection接口扩展了Iterable接口。
一些常用的Set实现类是HashSet，LinkedHashSet，TreeSet，
CopyOnWriteArraySet和ConcurrentSkipListSet。 
AbstractSet提供了Set接口的骨干实现，以减少实现Set的工作量。

一些有用的Java Set方法：

	int size（）：获取Set中的元素数。
	boolean isEmpty（）：检查Set是否为空。
	boolean contains（Object o）：如果此Set包含指定的元素，则返回true。
	Iterator iterator（）：返回此set中元素的迭代器。元素按特定顺序返回。
	Object [] toArray（）：返回包含此set中所有元素的数组。如果此set对其迭代器返回的元素的顺序做出任何保证，则此方法必须以相同的顺序返回元素。
	boolean add（E e）：如果指定的元素尚不存在，则将其添加到该集合中（可选操作）。
	boolean remove（Object o）：如果存在，则从该集合中移除指定的元素（可选操作）。
	boolean removeAll（Collection c）：从此set中移除指定collection中包含的所有元素（可选操作）。
	boolean retainAll（Collection c）：仅保留此set中包含在指定collection中的元素（可选操作）。
	void clear（）：从集合中删除所有元素。
	Iterator iterator（）：返回此set中元素的迭代器。

在Java SE 9发行版中，Oracle Corp将为Set接口添加一些有用的实用方法。


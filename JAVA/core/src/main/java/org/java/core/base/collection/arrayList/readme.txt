ArrayList继承自AbstractList，AbstractList是一个List接口的骨干实现，也就是实现了List接口的大部分方法.

当然ArrayList也implements了List:

class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable.
abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>.

### ArrayList常用方法

> ArrayList常用的方法和Collection基本一样.

add(2, "CC");将指定的元素插入此列表中的指定位置。索引从0开始
addAll(list2);将形参中的集合元素全部添加到当前集合中的尾部。
addAll(2, list2);将形参中集合元素添加到当前集合指定的位置。
> 注意：以下方法和Collection一样，都依赖元素对象的equals方法。更多的时候都需要重写equals方法。
6.contains("aa");返回当前元素是否包含某一个对象
7.get(1);获取当前集合中指定位置的元素
8.indexOf("BB");返回当前集合中首次出现形参对象的位置，如果集合中不存在就返回-1.
* size();放回当前集合元素个数.
* isEmpty();判断集合是否为空，返回布尔类型的结果。
* lastIndexOf(Object o);返回集合中最后一次出现形参元素的索引，不存在就返回-1。
* toArray();将集合转换为数组
* set(int index,E element);用指定元素替代集合中指定位置的元素。
* remove(Object o);移除集合中首次出现的元素。
* remove(int index);移除集合中指定位置的元素。

### ArrayList注意事项

1. Java ArrayList几乎与Vector类似，只是它不同步，因此在单线程环境中性能更好。
2. Java ArrayList不是线程安全的，因此在多线程环境中使用时必须特别小心。
3. Java ArrayList可以包含重复值，它还允许“null”值。
4. java ArrayList中的对象按顺序添加。 因此，您始终可以通过索引0检索第一个对象。
5. Java ArrayList默认容量定义为10.但是我们可以通过它的构造函数或通过调用ensureCapacity（int minCapacity）方法来更改默认容量。
6. Java ArrayList Iterator和ListIterator实现是快速失败的。 如果在iterator创建后修改列表结构, 
 除去用迭代器自身的add或remove之外的任何其他方式修改列表结构，则会抛出ConcurrentModificationException。
7. Java ArrayList提供对其元素的随机访问，因为它适用于索引。 我们可以通过它的索引检索任何元素。
8. Java ArrayList支持Generics，它是创建ArrayList的推荐方法。

共有3中构造器:
1. ArrayList(): 返回空列表并且初始容量为10.
2. ArrayList(int initialCapacity):返回空列表并且初始容量initialCapacity，
当您知道列表中包含大量数据并且希望通过提供大量初始容量来节省重新分配时间时，此构造函数非常有用。
3. ArrayList(Collection<? extends E> c):此ArrayList构造函数将按照

Java ArrayList不是线程安全的。 因此，如果集合的迭代器返回的顺序返回包含指定集合元素的列表。
您在多线程环境中工作，请使用下面的代码来获取线程安全的ArrayList:

List<Integer> synchronizedList = Collections.synchronizedList(ints);

## ArrayList实现原理（源码解读）

* ArrayList是List接口的可变数组的实现，允许包括null在内的所有元素，既然是数组，那么该类肯定会存在改变存储列表的数组大小的方法。
* 每一个ArrayList实例都有一个容量，该容量是用来存储列表元素的数组的大小，他总是等于列表的大小，
随着往ArrayList中添加元素，这个容量也会相应的总动增长，自动增长就会带来数据向新数组的重新拷贝。

#### 如何实现扩容的？

从上面的代码可以看出，数组进行扩容时，会将老数组的所有元素拷贝到新数组中，其中新数组的大小是老数组大小右移一位再加上老数组的大小。

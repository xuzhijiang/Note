List的接口继承关系:

	public interface List<E> extends Collection<E>.

	public interface Collection<E> extends Iterable<E>.
	
	public interface Iterable<T>.

Java 1.5 came up with thread-safe collection classes that allowed to modify Collections
while iterating over it, some of them are CopyOnWriteArrayList, ConcurrentHashMap, 
CopyOnWriteArraySet. These classes are in java.util.concurrent package. All the collection 
classes are present in java.util and java.util.concurrent package.

Java1.5提出了线程安全的集合类，允许当迭代它的时候修改集合，他们中的一些是CopyOnWriteArrayList, ConcurrentHashMap, 
CopyOnWriteArraySet, 这些类是在java.util.concurrent保重，所有的这些集合类都存在于java.util和java.util.concurrent包中.

#### 1.ArrayList
由数组方式实现数据存储，当然数组的访问速度比链表快。源码如下：
```java
public class ArrayList<E> extends AbstractList<E>
    implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
private static final long serialVersionUID = 8683452581122892189L;

/**
    * The array buffer into which the elements of the ArrayList are stored.
    * The capacity of the ArrayList is the length of this array buffer.
    */
private transient Object[] elementData;
```
#### 2.LinkedList
由连接实现数据的存储，随机添加和删除元素的的操作的效率要高于数组，简单说就更适用于频繁的插入和实现操作。
#### 3.Vector
是一种古老的实现类，在Vector类中，里面的方法添加了synchronized修饰，是线程安全的，他的性能比ArrayList差，用的地方比较少，

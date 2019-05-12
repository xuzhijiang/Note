## List

List接口是`有序的可重复的`集合，可以通过索引来访问List中的元素,list内部维护了一个动态数组，当数组满了，再次插入数据到list当中的时候，list会自动扩容。使用List接口能够精确的控制每一个元素插入的位置。

* ArrayList采用数组实现，数组的访问速度要比链表快，所以ArrayList更适合查询操作，
* LinkedList采用链表实现，插入和删除的效率要高于数组，
* Vector是一种古老的实现类，采用数组的实现，内部方法使用了Sychronized关键字，是线程安全的。

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

### LinkedList

由连接实现数据的存储，随机添加和删除元素的的操作的效率要高于数组，简单说就更适用于频繁的插入和实现操作。

```java
public class LinkedList<E>
extends AbstractSequentialList<E>
implements List<E>, Deque<E>, Cloneable, java.io.Serializable{}
```

* LinkList继承了AbstractSequentialList的双向链表，这个和ArrayList不同,ArrayList继承的是AbstractList。
* LinkList实现了List接口，能对它进行队列操作。
* LinkList实现了Deque接口，表明可以用作双向队列操作。
* LinkList实现了Cloneable接口，表明可以实现克隆操作。
* Linklist实现了Serializable接口，表明支持序列化，可以通过序列化传输。

#### LinkedList继承AbstractSequentialList的必然性

>AbstractSequentialList的源码如下：

```java
public abstract class AbstractSequentialList<E> extends AbstractList<E> {}
```

>可以看出AbstractSequentialList这个类是一个抽象类，而且也继承了AbstractList类，再看里面的方法，如下：

```java
public E get(int index) {
    try {
        return listIterator(index).next();
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public E set(int index, E element) {
    try {
        ListIterator<E> e = listIterator(index);
        E oldVal = e.next();
        e.set(element);
        return oldVal;
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public void add(int index, E element) {
    try {
        listIterator(index).add(element);
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}

public E remove(int index) {
    try {
        ListIterator<E> e = listIterator(index);
        E outCast = e.next();
        e.remove();
        return outCast;
    } catch (NoSuchElementException exc) {
        throw new IndexOutOfBoundsException("Index: "+index);
    }
}
```

>从上述代码可以看出，AbstractSequentialList类实现了这个操作集合的基本也是骨干的方法，
LinkList是双向链表，他继承了AbstractSequentialList这个类，就相当于已经实现了这些方法。
注意，除了正向迭代器，还可以使用反向迭代器:使用descendingIterator()方法

#### Vector

是一种古老的实现类，在Vector类中，里面的方法添加了synchronized修饰，是线程安全的，他的性能比ArrayList差，用的地方比较少，

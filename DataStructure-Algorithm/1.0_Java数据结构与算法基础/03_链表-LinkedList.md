## LinkedList-链表

>链表可能是继数组之后第二种使用最广泛的通用存储结构。

### 链表分类:

* 单端链表
* 双端链表
* 有序链表
* 双向列表
* 有迭代器的列表

### 链表与数组的区别

在数组中，数据是存储在一段连续的内存空间中，而在链表中，元素是存储在不同的内存空间中，
前一个元素的位置维护了后一个元素在内存中的地址。事实上，对链表中元素的访问，都是从第一个Node中开始的，
第一个Node是整个链表的入口。而在数组中，我们可以通过下标进行访问元素。

### 单链表操作分析

* 插入操作分析：基于链表的特性，插入到链表的第一个位置是非常快的，因为只要改变fisrtNode的引用即
* 查找操作分析: 从链表的fisrtNode开始进行查找，如果确定Node中维护的data就是我们要查找的数据，
即返回，如果不是，根据next获取下一个节点，重复这些步骤，直到找到最后一个元素，如果最后一个都没找到，返回null。
* 删除操作分析: 首先查找到要删除的元素节点，同时将这个节点的上一个节点和下一个节点也要记录下来，
只要将上一个节点的next引用直接指向下一个节点即可，这就相当于 删除了这个节点。如果要删除的是第一个节点，
直接将LinkList的firstNode指向第二个节点即可。如果删除的是最后一个节点，只要将最后一个节点的上一个节点的next引用置为null即可。
上述分析，可以删除任意节点，具有通用性但是效率较低。通常情况下，我们还会提供一个removeFirst方法，
因为这个方法效率较高，同样只要改变fisrtNode的引用即可

### 有序链表Java实现

有序链表，使用SortedLinkList表示，所谓有序链表，就是链表中Node节点之间的引用关系是根据Node中维护的数据data的
某个字段为key值进行排序的。为了在一个有序链表中插入，算法必须首先搜索链表，直到找到合适的位置：
它恰好在第一个比它大的数据项前面。当算法找到了要插入的数据项的位置，用通常的方式插入数据项：
把新的节点Node指向下一个节点，然后把前一个节点Node的next字段改为指向新的节点。然而，需要考虑一些特殊情况，
连接点有可能插入在表头或者表尾。

我们创建一个类Person表示插入的数据，我们希望链表中数据是按照Person的enName属性升序排列的。  

### Java中的LinkedList

>LinkList采用的是双向链表的实现方式，基于链表的实现方式使得在插入和删除是比ArrayList要快，但是数组的随机访问要快,LinkedList采用的是双向链表非同步的方式实现，允许存储null在内的所有元素.

```java
public class LinkedList<E>
    extends AbstractSequentialList<E>
    implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
public E getFirst() ;
public E getLast() ;
public E removeFirst();
public E removeLast();
public void addFirst(E e);
public void addLast(E e);
public boolean contains(Object o);
public int size();
public boolean add(E e);//等价于addLast
public boolean remove(Object o);
public void clear() ;
....
}
```

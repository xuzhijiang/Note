ArrayList是使用最多的集合类，

ArrayList继承自AbstractList，AbstractList是一个List接口的骨干实现，也就是实现了List接口的大部分方法.

ArrayList的大小：

	Java ArrayList是List接口的可调整大小的数组实现，这意味着它以默认大小开始，
并在将更多数据添加到数组列表时自动增长.

当然ArrayList也implements了List:
class ArrayList<E> extends AbstractList<E>
implements List<E>, RandomAccess, Cloneable, java.io.Serializable.

abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>.

一些重要的方法是:

1. Java ArrayList几乎与Vector类似，只是它不同步，因此在单线程环境中性能更好。
2. Java ArrayList不是线程安全的，因此在多线程环境中使用时必须特别小心。
3. Java ArrayList可以包含重复值，它还允许“null”值。
4. java ArrayList中的对象按顺序添加。 因此，您始终可以通过索引0检索第一个对象。
5. Java ArrayList默认容量定义为10.但是我们可以通过它的构造函数或通过调用ensureCapacity（int minCapacity）方法来更改默认容量。
6. Java ArrayList Iterator和ListIterator实现是快速失败的。 如果在iterator创建后修改列表结构, 
 除去用迭代器自身的add或remove之外的任何其他方式修改列表结构，则会抛出ConcurrentModificationException。
7. Java ArrayList提供对其元素的随机访问，因为它适用于索引。 我们可以通过它的索引检索任何元素。
8. Java ArrayList支持Generics，它是创建ArrayList的推荐方法。
// List list = new ArrayList(); //not recommended
// List<String> list1 = new ArrayList<String>(); // recommended way

共有3中构造器:
1. ArrayList(): 返回空列表并且初始容量为10.
2. ArrayList(int initialCapacity):返回空列表并且初始容量initialCapacity，
当您知道列表中包含大量数据并且希望通过提供大量初始容量来节省重新分配时间时，此构造函数非常有用。
3. ArrayList(Collection<? extends E> c):此ArrayList构造函数将按照
集合的迭代器返回的顺序返回包含指定集合元素的列表。

// Java ArrayList default constructor
List<String> vowels = new ArrayList<String>();

//Java ArrayList constructor with initial capacity
List<String> dictionaryWordsList = new ArrayList<String>(50000);

vowels.add("A");
vowels.add("B");
vowels.add("C");
vowels.add("D");
vowels.add("E");

//Creating my list from different collection source
List<String> myList = new ArrayList<String>(vowels);

	线程安全:
		
		Java ArrayList不是线程安全的。 因此，如果您在多线程环境中工作，请使用下面的代码来获取线程安全的ArrayList:

List<Integer> synchronizedList = Collections.synchronizedList(ints);


## ArrayList

#### ArrayList常用方法
ArrayList常用的方法和Collection基本一样，也大致看看吧，就当复习吧。
##### 1.add("AA");向集合中添加一个元素
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
}
```
##### 2.add(2, "CC");将指定的元素插入此列表中的指定位置。索引从0开始
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("DD");
    System.out.println(list);
    list.add(2, "CC");
    System.out.println(list);
}
```
结果为：
```properties
	[AA, BB, DD]
	[AA, BB, CC, DD]
```
##### 3.addAll(list2);将形参中的集合元素全部添加到当前集合中的尾部。
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("DD");
    List<String> list2 = new ArrayList<String>();
    list2.add("CC");
    list2.add("EE");
    list.addAll(list2);
    System.out.println(list);
}
```
结果为：
```properties
[AA, BB, DD, CC, EE]
```
##### 4.addAll(2, list2);将形参中集合元素添加到当前集合指定的位置。
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("DD");
    List<String> list2 = new ArrayList<String>();
    list2.add("CC");
    list2.add("EE");
    list.addAll(2, list2);
    System.out.println(list);
}
```
结果为：
```properties
	[AA, BB, CC, EE, DD]
```
##### 5.clear();清空当前集合中所有元素
```java
list.clear();
```
> 注意：以下方法和Collection一样，都依赖元素对象的equals方法。更多的时候都需要重写equals方法。
##### 6.contains("aa");返回当前 元素是否包含某一个对象。当前放回false。
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("DD");
    boolean contains = list.contains("aa");
    System.out.println(contains);
}
```
##### 7.get(1);获取当前集合中指定位置的元素，这里返回BB
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("DD");
    String string = list.get(1);
    System.out.println(string);
}
```
##### 8.indexOf("BB");返回当前集合中首次出现形参对象的位置，如果集合中不存在就返回-1.
```java
public static void main(String[] args) {
    List<String> list = new ArrayList<String>();
    list.add("AA");
    list.add("BB");
    list.add("BB");
    int indexOf = list.indexOf("BB");
    System.out.println(indexOf);
}
```
#### 简单的方法就查询jdkAPI文档吧，下面做简要的说明：
* size();放回当前集合元素个数.
* isEmpty();判断集合是否为空，返回布尔类型的结果。
* lastIndexOf(Object o);返回集合中最后一次出现形参元素的索引，不存在就返回-1。
* toArray();将集合转换为数组
* set(int index,E element);用指定元素替代集合中指定位置的元素。
* remove(Object o);移除集合中首次出现的元素。
* remove(int index);移除集合中指定位置的元素。
## ArrayList实现原理（源码解读）
* ArrayList是List接口的可变数组的实现，允许包括null在内的所有元素，既然是数组，那么该类肯定会存在改变存储列表的数组大小的方法。
* 每一个ArrayList实例都有一个容量，该容量是用来存储列表元素的数组的大小，他总是等于列表的大小，随着往ArrayList中添加元素，这个容量也会相应的总动增长，自动增长就会带来数据向新数组的重新拷贝。
#### 1.底层使用数组实现
```java
private transient Object[] elementData;
```
#### 2.构造方法如下：
```java
public ArrayList() {
    this(10);
}
    public ArrayList(int initialCapacity) {
    super();
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal Capacity: "+
                                            initialCapacity);
    this.elementData = new Object[initialCapacity];
}
    public ArrayList(Collection<? extends E> c) {
    elementData = c.toArray();
    size = elementData.length;
    // c.toArray might (incorrectly) not return Object[] (see 6260652)
    if (elementData.getClass() != Object[].class)
        elementData = Arrays.copyOf(elementData, size, Object[].class);
}
```
在初始化ArrayList实例时，可以构造一个初始容量为10的空列表，也可以构造一个指定初始容量的空列表以及构造一个包含指定Collection元素的列表。
#### 3.如何实现存储的？
##### 3.1使用set(int index, E element) ;方法，用指定的元素替代指定位置的元素
```java
public E set(int index, E element) {
    rangeCheck(index);

    E oldValue = elementData(index);
    elementData[index] = element;
    return oldValue;
}
```
##### 3.2add(E e);将指定的元素添加到集合尾部
```java
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
```
##### 3.3add(int index, E element)将指定的元素插入此列表中的指定位置
```java
public void add(int index, E element) {
    if (index > size || index < 0)
        throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size);
    // 如果数组长度不足，将进行扩容。
    ensureCapacity(size+1);  // Increments modCount!!
    // 将 elementData中从Index位置开始、长度为size-index的元素，
    // 拷贝到从下标为index+1位置开始的新的elementData数组中。
    // 即将当前位于该位置的元素以及所有后续元素右移一个位置。
    System.arraycopy(elementData, index, elementData, index + 1, size - index);
    elementData[index] = element;
    size++;
}
```
#### 4.如何读取元素？
```java
public E get(int index) {
    rangeCheck(index);

    return elementData(index);
}
```
#### 5.删除元素
```java
public boolean remove(Object o) {
    if (o == null) {
        for (int index = 0; index < size; index++)
            if (elementData[index] == null) {
                fastRemove(index);
                return true;
            }
    } else {
        for (int index = 0; index < size; index++)
            if (o.equals(elementData[index])) {
                fastRemove(index);
                return true;
            }
    }
    return false;
}
```
注意：从集合中移除一个元素，如果这个元素不是最后一个元素，那么这个元素的后面元素会想向左移动一位。
#### 5.如何实现扩容的？
```java
public void ensureCapacity(int minCapacity) {
    if (minCapacity > 0)
        ensureCapacityInternal(minCapacity);
}
    private void ensureCapacityInternal(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}
    private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```
从上面的代码可以看出，数组进行扩容时，会将老数组的所有元素拷贝到新数组中，
其中新数组的大小是老数组大小右移一位再加上老数组的大小。
#### 6.手动调整底层数组的容量为列表实际元素大小的方法
```java
public void trimToSize() {
    modCount++;
    int oldCapacity = elementData.length;
    if (size < oldCapacity) {
        elementData = Arrays.copyOf(elementData, size);
    }
}
```


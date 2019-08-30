package org.java.core.base.collection.list.arrayList.source;

import java.util.Arrays;
import java.util.Collection;

/**
 * @since 1.7
 *
 * ArrayList的注意点:
 * 1. 当数据量很大的时候，ArrayList内部操作元素的时候会移动位置，很耗性能
 * 2. ArrayList虽然可以自动扩展长度，但是数据量一大，扩展的也多，会造成很多空间的浪费
 * 3. ArrayList有一个内部私有类，SubList。ArrayList提供一个subList方法用于构造这个SubList。这里需要注意的是SubList和ArrayList使用的数据引用是同一个对象，在SubList中操作数据和在ArrayList中操作数据都会影响双方。
 * 4. ArrayList允许加入null元素
 * 5. 由数组方式实现数据存储，当然数组的访问速度比链表快
 *
 * size();放回当前集合元素个数.
 * isEmpty();判断集合是否为空，返回布尔类型的结果。
 * lastIndexOf(Object o);返回集合中最后一次出现形参元素的索引，不存在就返回-1。
 * toArray();将集合转换为数组
 * set(int index,E element);用指定元素替代集合中指定位置的元素。
 * remove(Object o);移除集合中首次出现的元素。
 * remove(int index);移除集合中指定位置的元素。
 * get(1);获取当前集合中指定位置的元素
 * contains("aa");返回当前元素是否包含某一个对象(依赖元素对象的equals方法)
 * indexOf("BB");返回当前集合中首次出现形参对象的位置，如果集合中不存在就返回-1.
 */
public class ArrayListSource<E> {

    private static final int DEFAULT_CAPACITY = 10; // 集合的默认容量

    //用于默认大小空实例的共享空数组实例,容量为0
    private static final Object[] EMPTY_ELEMENTDATA = {};

    private transient Object[] elementData; // 存储集合数据的内部数组，默认值为null

    /**
     * ArrayList 所包含的元素个数
     */
    private int size;

    /**
     * 此列表已被结构化修改的次数。结构化修改是那些改变列表大小的修改，
     * 或以其他方式扰乱它。
     *
     * 该字段由迭代器和列表迭代器实现使用。
     * 由iterator和listIterator方法返回。
     * 如果此字段的值意外更改，迭代器（或列表迭代器）将抛出一个ConcurrentModificationException
     * 来对 next,  remove,  previous, set or add 这些操作做出响应。
     * 这提供了fail-fast行为，而不是在迭代期间并发修改表面的非确定性行为
     */
    protected transient int modCount = 0;// 此field是抽象父类AbstractList的字段.

    public ArrayListSource(int initialCapacity) { // 带有集合容量参数的构造函数
        // 调用父类AbstractList的方法构造函数
        super();
        if (initialCapacity < 0) // 如果集合的容量小于0，直接抛出异常
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        this.elementData = new Object[initialCapacity]; // 初始化elementData属性，确定容量
    }

    /**
     *默认构造函数，DEFAULTCAPACITY_EMPTY_ELEMENTDATA 为0.初始化为10，
     * 也就是说初始其实是空数组 当添加第一个元素的时候数组容量才变成10
     */
    public ArrayListSource() {
        super();
        this.elementData = EMPTY_ELEMENTDATA;
    }

    public ArrayListSource(Collection<? extends E> c) { // 参数是一个集合的构造函数
        elementData = c.toArray(); // elementData直接使用参数集合内部的数组
        size = elementData.length; // 初始化数组当前有效长度
        // c.toArray方法可能不会返回一个Object[]结果，需要做一层判断。
        // 这个一个Java的bug，可以在http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652查看
        if (elementData.getClass() != Object[].class) {
            elementData = Arrays.copyOf(elementData, size, Object[].class);
        }
    }

    public boolean add(E e) {
        // 确保内部的容量,参数是需要的大小。
        // 确保集合容量够大，不够的话需要扩容
        ensureCapacityInternal(size + 1);
        // 数组容量够的话，直接添加元素到数组最后一个位置即可，同时修改集合当前有效长度
        elementData[size++] = e;
        return true;
    }

    // 确保内部的容量足够,
    // 当前集合只要能够容纳minCapacity个元素,否则就需要扩容
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENTDATA) { // 如果数组是个空数组，说明调用的是无参的构造函数
            // 如果调用的是无参构造函数，说明数组容量为0，那就需要使用默认容量10
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        ensureExplicitCapacity(minCapacity);
    }

    // 确保明确的容量
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // 如果集合需要的最小长度比数组容量要大，那么就需要扩容，已经放不下了
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }

    // The maximum size of array to allocate.
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private void grow(int minCapacity) { // 扩容的实现
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // 长度扩大1.5倍
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 将数组拷贝到新长度的数组中
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }

    // 在指定位置插入数据，该方法的缺点就是如果集合数据量很大，移动元素位置将会话费不少时间：
    public void add(int index, E element) {
        rangeCheckForAdd(index); // 检查索引位置的正确的，不能小于0也不能大于数组有效长度

        ensureCapacityInternal(size + 1);  // 扩容检测
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index); // 移动数组位置，数据量很大的话，性能变差
        elementData[index] = element; // 指定的位置插入数据
        size++; // 数组有效长度+1
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    // 移除对应坐标值上的数据
    // 比如要移除5个元素中的第3个元素，首先要把4和5这2个位置的元素分别set到3和4这2个位置上，
    // set完之后最后一个位置也就是第5个位置set为null。
    public E remove(int index) {
        rangeCheck(index); // 检查索引值是否合法

        modCount++;
        E oldValue = elementData(index); // 得到对应索引位置上的元素

        int numMoved = size - index - 1; // 需要移动的数量
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved); // 从后往前移，留出最后一个元素
        elementData[--size] = null; // 清除对应位置上的对象，让gc回收

        return oldValue;
    }

    E elementData(int index) {
        return (E) elementData[index];
    }

    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    // 找出数组中的元素，然后移除
    // 跟remove索引元素一样，这个方法是根据equals比较
    public boolean remove(Object o) {
        if (o == null) {
            // ArrayList允许元素为null，所以对null值的删除在这个分支里进行
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
        } else {
            // 效率比较低，需要从第1个元素开始遍历直到找到equals相等的元素后才进行删除，删除同样需要移动元素
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                    numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }

    // 清除list中的所有数据
    public void clear() {
        modCount++;

        // 遍历集合数据，全部set为null
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0; // 数组有效长度变成0
    }

    // 用element值替换下标值为index的值
    public E set(int index, E element) {
        rangeCheck(index); // 检查索引值是否合法

        E oldValue = elementData(index);
        elementData[index] = element; // 直接替换
        return oldValue;
    }

    public E get(int index) {
        rangeCheck(index); // 检查索引值是否合法

        return elementData(index); // 直接返回下标值
    }

    // 在列表的结尾添加一个Collection集合
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // 扩容检测
        // 直接在数组后面添加新的数组中的所有元素
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew; // 更新有效长度
        return numNew != 0;
    }

    // 根据elementData数组拷贝一份新的数组
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

}

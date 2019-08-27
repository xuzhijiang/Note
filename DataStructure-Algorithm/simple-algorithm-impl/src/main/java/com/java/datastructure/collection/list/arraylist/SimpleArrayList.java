package com.java.datastructure.collection.list.arraylist;

/**
 * 一个简单的ArrayList实现
 */
public class SimpleArrayList<T> {

    // SimpleArrayList的默认容量
    private static final Integer DEFUALT_CAPACITY = 16;

    //当数组容量不够时，默认每次扩容的大小
    private static final Integer DEFUALT_EXPAND_SIZE=16;

    // 当前List拥有元素的数量
    private int element_size = 0;

    // SimpleArrayList的容量
    private int array_capacity;

    Object[] array;

    public SimpleArrayList() {
        this(DEFUALT_CAPACITY);
    }

    /**
     * @param array_capacity SimpleArrayList的容量
     */
    public SimpleArrayList(Integer array_capacity) {
        if (array_capacity <= 0) {
            throw new IllegalArgumentException("array_capacity must > 0");
        }
        array = new Object[array_capacity];
        this.array_capacity = array_capacity;
    }

    /**
     * 插入一个新元素，如果数组可以放下，直接添加,如果数组中放不下，扩容
     */
    public void add(T element) {
        if (element_size < array_capacity) {//如果数组可以放下，直接添加
            array[element_size++] = element;
        } else {//如果数组放不下，扩容后再添加
            // 在这里我们使用需要考虑的一个问题是：一次扩容大小到底设置为多少比较合适。
            // 因为一旦扩容的话，就会使用到System.arraycopy()这个方法,会降低性能,
            // 如果插入的数据量比较大，就会频繁的扩容，就会导致频繁的拷贝,这种样性能是比较低的。
            array_capacity += DEFUALT_EXPAND_SIZE;
            Object[] new_array = new Object[array_capacity];
            System.arraycopy(array, 0, new_array, 0, array.length);
            array = new_array;
            array[element_size++] = element;
        }
    }

    /**
     * 根据指定下标查找元素
     */
    public T get(int index) {
        if(index < 0 || index > (element_size-1)) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (T) array[index];
    }

    /**
     * 删除指定位置的元素，所有之后的元素需要前移
     */
    public void remove(int index) {
        if(index<0||index>element_size-1){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        for(int i=index;i<element_size-1;i++){
            array[i] = array[i+1];
        }
        element_size--;
    }

    /**
     * 更新指定位置上的元素
     */
    public void update(int index, T element) {
        if(index<0||index>element_size-1){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        array[index]=element;
    }

    /**
     * 返回SimpleArrayList中当前元素的个数.
     */
    public int size(){
        return element_size;
    }

    public int capacity(){
        return array_capacity;
    }

}

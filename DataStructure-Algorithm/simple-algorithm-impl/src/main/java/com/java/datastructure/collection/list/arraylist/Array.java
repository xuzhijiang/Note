package com.java.datastructure.collection.list.arraylist;

public class Array<V> {

    private Object[] elements;

    // 当前数组拥有元素的数量
    private int size = 0;

    //数组的容量，初始化数组后，容量就不可更改.
    private int capacity;

    /**
     * @param capacity 数组的容量
     */
    public Array(int capacity) {
        this.capacity = capacity;
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must > 0");
        }
        elements = new Object[capacity];
    }

    public void insert(V v) {
        // 当前数组用于的元素数量已经等于了数组的容量
        if(size == capacity) {
            throw new IndexOutOfBoundsException();
        }
        elements[size++] = v;//插入元素
    }

    /**
     * 注意,可能Array中包含了多个相同的元素,所以这里只是remove第一个找到的元素.
     */
    public boolean remove(V v) {
        for(int i=0;i < size;i++) {
            if (elements[i].equals(v)) {
                elements[i] = null;//置空索引为i的元素
                moveUp(i);// 将后面的所有数据项都前移一个位置
                size--;//元素数量-1
                return true;//找到第一个要删除的项，返回true
            }
        }
        return false;//所有元素都查找过了，依然没有找到要删除的项，return false
    }

    // 找到第一个等于v的元素,(Array内部可能还有多个)
    public V find(V v) {
        for(int i=0;i<size;i++) {
            if(elements[i].equals(v)) {
                return (V)elements[i];
            }
        }
        return null;
    }

    /**
     * @param i 要删除的元素所在位置的索引
     */
    private void moveUp(int i) {
        // size - 1: 最后一个元素的索引
        while (i < size - 1) {
            elements[i] = elements[++i];
        }
        elements[size-1] = null;//最后一个元素置位null
    }

    /**
     * 返回指定下标的元素
     */
    public V get(int index){
        if (index > capacity - 1) {
            throw new IndexOutOfBoundsException();
        }
        return (V) elements[index];
    }

    /**
     * 返回数组中元素的数量
     */
    public int size(){
        return size;
    }

    public void display(String prefix){
        System.out.println();
        System.out.println(prefix);
        for (int i = 0; i < elements.length; i++) {
            System.out.println("第" + i + "个元素: " + elements[i]);
        }
    }

}

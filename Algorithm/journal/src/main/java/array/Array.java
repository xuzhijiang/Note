package array;

// T is meant to be a Type
// E is meant to be an Element (List<E>: a list of Elements)
// K is Key (in a Map<K,V>)
// V is Value (as a return value or mapped value)
// N - Number

public class Array<V> {

    private Object[] elements;
    private int size = 0;//当前数组拥有元素的数量
    private int capacity;//数组的容量

    /**
     *
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

    public boolean remove(V v) {
        for(int i=0;i<size;i++) {
            if(elements[i].equals(v)) {
                elements[i] = null;//置空索引为i的元素
                moveUp(i, size);// 将后面的所有数据项都前移一个位置
                size--;//元素数量-1
                return true;//找到第一个要删除的项，返回true
            }
        }
        return false;//所有元素都查找过了，依然没有找到要删除的项，return false
    }

    public V find(V v) {
        for(int i=0;i<size;i++) {
            if(elements[i].equals(v)) {
                return (V)elements[i];
            }
        }
        return null;
    }

    /**
     *
     * @param i 要删除的元素所在位置的索引
     * @param size 当前数组拥有元素的数量
     */
    private void moveUp(int i, int size) {
        // size-1：最后一个元素的索引
        while(i < size - 1){
            elements[i] = elements[++i];
        }
        elements[size-1] = null;//最后一个元素置位null
    }

    /**
     * 返回指定下标的元素
     * @param index
     * @return
     */
    public V get(int index){
        if(index>capacity-1){
            throw new IndexOutOfBoundsException();
        }
        return (V) elements[index];
    }

    /**
     * 返回数组中元素的数量
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * 显示所有元素
     */
    public void display(String prefix){
        System.out.print(prefix);
        for (int i = 0; i < elements.length; i++) {
            System.out.print(elements[i]+"    ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Array<Integer> array = new Array<Integer>(5);
        array.insert(1);
        array.insert(5);
        array.insert(3);
        array.display("初始 1、5、3 ： ");
        array.insert(4);
        array.insert(6);
        array.display("添加 4,6       ： ");
        array.remove(3);
        array.display("删除 3       ： ");
        System.out.println("查找 4："+array.find(4) );
        System.out.println("查找 3："+array.find(3) );
    }
}

package com.java.algorithm.arraylist;

/**
 * 一个简单的ArrayList实现
 */
public class SimpleArrayList<T> {

    // 数组中元素的大小
    private Integer element_size = 0;
    // 创建SimpleArrayList时，数组的容量，默认为16
    private Integer array_capacity = 16;
    //当数组容量不够时，默认每次扩容的大小
    private static final Integer DEFUALT_EXPAND_SIZE=16;

    Object[] array = null;

    public SimpleArrayList() {
        this(DEFUALT_EXPAND_SIZE);
    }

    /**
     * @param array_capacity 数组大小
     */
    public SimpleArrayList(Integer array_capacity) {
        super();
        if (array_capacity <= 0) {
            throw new IllegalArgumentException("array_capacity must > 0");
        }
        array = new Object[array_capacity];
        this.array_capacity = array_capacity;
    }

    /**
     * 插入一个新元素，如果数组可以放下，直接添加
     * 如果数组中放不下，扩容
     */
    public void add(T element) {
        if (element_size < array_capacity) {//如果数组可以放下，直接添加
            array[element_size++] = element;
        } else {//如果数组放不下，扩容后再添加
            array_capacity += DEFUALT_EXPAND_SIZE;
            Object[] new_array = new Object[array_capacity];
            System.arraycopy(array, 0, new_array, 0, array.length);
            array = new_array;
            array[element_size++] = element;
        }
    }

    /**
     * 根据指定下标查找元素
     * @param index
     * @return
     */
    public T get(int index) {
        if(index < 0 || index > (element_size-1)) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return (T) array[index];
    }

    /**
     * 删除指定位置的元素，所有之后的元素需要前移
     * @param index
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
     * @param index
     * @param element
     */
    public void update(Integer index, T element) {
        if(index<0||index>element_size-1){
            throw new ArrayIndexOutOfBoundsException(index);
        }
        array[index]=element;
    }

    /**
     * 返回array中元素的大小
     * @return
     */
    public Integer size(){
        return element_size;
    }

    public Integer capacity(){
        return array_capacity;
    }


    public static void main(String[] args) {
        //testInsert();

//        testTraverse();

        testDelete();
    }
    /**
     * 测试插入，注意我们使用的无参构造方法，因此默认容量大小是16 而我们插入的是20个元素，
     因此我们的SimpleArrayList会自动扩容
     */
    public static void testInsert() {
        SimpleArrayList<Integer> list = new SimpleArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        System.out.println("size:" + list.size() + ",capacity:" + list.capacity());
        // 因为我们插入20个元素，超出默认大小16，因此会扩容，而一次扩容16，所以扩容capacity到32.
        // 在这里我们使用需要考虑的一个问题是：一次扩容大小到底设置为多少比较合适。
        // 因为一旦扩容的话，就会使用到System.arraycopy()这个方法。如果插入的数据量比较大，
        // 就会频繁的扩容，这种样性能是比较低的。因此我们的SimpleArrayList，还可以提供一个参数，
        // 让用户指定每次扩容的大小，这样如果有大量的数据要插入的话，可以减少数组拷贝的次数。
    }

    /**
     * 遍历
     */
    public static void testTraverse() {
        SimpleArrayList<Integer> list = new SimpleArrayList<Integer>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+" ");
        }
    }

    /**
     * 测试删除
     */
    public static void testDelete() {
        // 准备数组
        SimpleArrayList<Integer> list = new SimpleArrayList<Integer>();
        for (int i = 0; i <20; i++) {
            list.add(i);
        }

        //删除index为10的元素
        list.remove(10);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("index:"+i+";value:"+list.get(i));
        }
        System.out.println("size:" + list.size() + ",capacity:" + list.capacity());
    }
}

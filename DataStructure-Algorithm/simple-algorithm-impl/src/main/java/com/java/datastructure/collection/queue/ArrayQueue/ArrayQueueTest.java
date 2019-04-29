package com.java.datastructure.collection.queue.ArrayQueue;

/**
 * 本测试演示上述分析插入字母案例，因为A-P共有16个字母。而16个字母插满之后，还有3个空余位置，说明队列大小为19
 * 因此在每个测试方法中，我们都设置队列初始大小为16
 * @author Administrator
 *
 */
public class ArrayQueueTest {
    private static char begin='A';
    private static char end='P';

    public static void main(String[] args){
        testInsertA_P();

        testInsertRemoveInsert();
    }

    /**
     * 队列中插入16个元素A-P，观察front、rear指针的位置
     * 一般情况下，插入操作是在队列不满的情况下，才调用。因此在插入前，应该先调用isFull()
     * 如果队列中元素已经满了，就不应该继续插入
     */
    public static void testInsertA_P() {
        ArrayQueue<Character> queue=new ArrayQueue<Character>(19);
        for (char i =  begin; i <= end; i++) {
            if(!queue.isFull()){
                queue.enqueue(i);
            }
        }
        System.out.println(queue);

    }

    /**
     * 测试添加之后，再删除
     */
    public static void testInsertRemoveInsert() {
        //初始数据，队列中有5个元素
        char current=0;
        ArrayQueue<Character> queue=new ArrayQueue<Character>(19);
        for (char i = begin; i <= end; i++) {
            if(!queue.isFull()){
                queue.enqueue(i);
                current=i;
            }
        }

        System.out.println(queue);
        System.out.println("初始数据:\n"+queue);

        //移除3个元素
        System.out.println("移除队列首部的5个元素:");
        for (int i = 0; i < 5; i++) {
            if(!queue.isEmpty()){
                queue.dequeue();
            }
        }

        System.out.println(queue);
        System.out.println("添加元素5个元素，数组尾部只剩3个位置，因此有个元素要添加到队列首部：");

        int endChar = current+5;
        for (; current <= endChar; current++) {
            if(!queue.isFull()){
                queue.enqueue(current);
            }
        }
        System.out.println(queue);

    }

}
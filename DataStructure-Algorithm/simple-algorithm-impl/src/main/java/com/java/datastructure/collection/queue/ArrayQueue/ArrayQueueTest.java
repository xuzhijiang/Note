package com.java.datastructure.collection.queue.ArrayQueue;

import org.junit.Test;

public class ArrayQueueTest {

    private static char begin='A';

    private static char end='P';

    /**
     * 队列中插入16个元素A-P，观察front、rear指针的位置
     */
    @Test
    public void testInsertA_P() {
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
    @Test
    public void testInsertRemoveInsert() {
        //初始数据，队列中有5个元素
        char current=0;
        ArrayQueue<Character> queue=new ArrayQueue<Character>(19);
        for (char i = begin; i <= end; i++) {
            if(!queue.isFull()){
                queue.enqueue(i);
                current=i;
            }
        }
        System.out.println("初始数据:\n"+queue);

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
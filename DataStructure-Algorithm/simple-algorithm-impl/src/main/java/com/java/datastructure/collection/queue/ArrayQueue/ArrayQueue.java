package com.java.datastructure.collection.queue.ArrayQueue;

import java.util.Arrays;

/**
 * 本例是基于数组实现的Queue
 *
 * 队列的特点是FIFO先进先出，
 */
public class ArrayQueue<T> {

    Object[] data = null;

    // 对头指针
    private int front = 0;

    // 队尾指针
    private int rear = -1;

    // 队列中当前元素的数量
    private int size = 0;

    // 队列的容量
    private int capacity;

    public ArrayQueue(int capacity){
        this.capacity = capacity;
        data = new Object[capacity];
    }

    /**
     * 入队,入队只会影响rear
     */
    public void enqueue(T element){
        // 一般情况下，插入操作是在队列不满的情况下，才调用。因此在插入前，应该先调用isFull
        if(isFull()){
            throw new IllegalStateException("queue is full");
        }
        // 在队列中插入元素，正常情况下是在队尾指针(rear)+1的位置上插入，因为我们编写的是循环队列
        // 因此，当队尾指针指向数组顶端的时候，我们要将队尾指针(rear)重置为-1，此时再加1，就是0，也就是数组顶端
        isNeedCycle();
        data[++rear] = element;
        size++;
    }

    // 支持循环队列
    private void isNeedCycle(){
        // 如果rear = capacity - 1，说明下一个元素因该是的数组的首部，将rear置为-1
        // 因此下一个位置就是0
        if(rear == capacity - 1){
            rear = -1;
        }
    }

    // 出队，出队只会影响front
    public T dequeue(){
        if(isEmpty()){// 在remove之前，应该调用isEmpty，如果为空,throws exception.
            throw new IllegalStateException("no elements in the queue");
        }
        T t = (T) data[front];
        data[front] = null;
        front++;
        if(front == capacity){
            front = 0;
        }
        size--;
        return t;
    }

    // 查看队列首部元素，不移除
    public T peekFront(){
        if(isEmpty()){
            throw new IllegalStateException("no elements in the queue");
        }
        return (T) data[front];
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == capacity;
    }

    public int size(){
        return size;
    }

    public int capacity(){
        return capacity;
    }

    @Override
    public String toString() {
        return "ArrayQueue [container=" + Arrays.toString(data)
                + ", front=" + front + ", rear=" + rear + ", size="
                + size + ", capacity=" + capacity + "]";
    }

}

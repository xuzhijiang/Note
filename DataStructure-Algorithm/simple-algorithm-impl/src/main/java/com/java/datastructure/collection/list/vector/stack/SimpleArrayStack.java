package com.java.datastructure.collection.list.vector.stack;

import java.util.Arrays;

public class SimpleArrayStack<T> {

    private Object[] array;
    private int size = 0;
    private static final int DEFAULT_INITIAL_SIZE = 10;
    private int capacity = 0;

    public SimpleArrayStack(){
        this(DEFAULT_INITIAL_SIZE);
    }

    public SimpleArrayStack(int initialSize){
        super();
        this.capacity = initialSize;
        array = new Object[initialSize];
    }

    public T pop(){
        if(size < 0){
            throw new IllegalArgumentException("no more elements");
        }

        T result = (T) array[--size];
        array[size] = null;
        return result;
    }

    public T peek(){
        if(size < 1){
            throw new IllegalArgumentException("no more elements");
        }
        return (T) array[size-1];
    }

    public void push(T e){
        if(size >= capacity){// 扩容
            int new_capacity = capacity + (capacity >> 1);
            if(new_capacity <= 0){
                new_capacity = Integer.MAX_VALUE;
            }
            array = Arrays.copyOf(array, new_capacity);
            capacity = new_capacity;
        }
        int index = size++;
        array[index] = e;
    }

    public int getSize(){
        return size;
    }

    public static void main(String[] args) {
        SimpleArrayStack<Integer> simpleStack=new SimpleArrayStack<Integer>();
        System. out.print("push:\t" );
        for (int i = 0; i < 1000; i++) {
            simpleStack.push(i );
            System.out.print(i +"\t" );
        }
        System.out.print("\npop:\t" );
        while (simpleStack.getSize()>0){
            System.out.print(simpleStack .pop()+"\t");
        }
    }
}

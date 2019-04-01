package com.java.algorithm.stack;

import com.java.algorithm.linkedlist.SingleLinkList;

public class SimpleLinkedListStack<T> {

    private SingleLinkList<T> singleLinkList = new SingleLinkList<T>();

    public void push(T t){
        singleLinkList.addFirst(t);
    }

    public T pop(){
        return singleLinkList.removeFirst();
    }

    public T peek(){
        return singleLinkList.getFirst();
    }

    public int getSize(){
        return singleLinkList.size();
    }

}

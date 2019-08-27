package com.java.datastructure.collection.list.vector.stack;

import com.java.datastructure.collection.list.linkedlist.SingleLinkList;

/**
 * 用链表实现的Stack(栈)
 */
public class SimpleLinkedListStack<T> {

    private SingleLinkList<T> singleLinkList = new SingleLinkList<>();

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

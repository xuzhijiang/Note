package com.java.datastructure.collection.list.linkedlist;

public class Node<T> {

    // Node 中维护的数据
    private T data;
    // 下一个元素的引用
    private Node<T> next;
    // 上一个元素的引用
    private Node<T> prev;

    Node() {

    }

    Node(Node<T> prev, T element, Node<T> next) {
        this.data = element;
        this.next = next;
        this.prev = prev;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

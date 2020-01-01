package com.java.datastructure.collection.list.linkedlist;

import java.util.Iterator;

/**
 * 单向链表
 *
 * 注意区分:
 * 双向链表: 维护firstNode和lastNode,每个node,维护了这个node的prev和这个node的next
 * 单向链表: 只维护firstNode,每个node,只维护了这个node的next.
 *
 * JDK的LinkedList是双向链表
 */
public class SingleLinkList<T> implements Iterable<T> {

    // 链表中第一个节点,作为整个链表的入口
    protected Node<T> firstNode;

    // 链表中维护的节点总量
    protected int size;

    // 添加到链表的最前面
    public void addFirst(T data) {
        Node node = new Node();
        node.setData(data);
        node.setNext(firstNode);
        firstNode = node;
        size++;
    }

    /**
     * 如果链表中包含要删除的元素，删除第一个匹配上的要删除的元素，
     * 并且返回true；如果没有找到要删除的元素，返回false
     */
    public boolean remove(Object data) {
        if (firstNode == null) {
            return false;
        }

        // 如果要删除的元素就是firstNode的话
        if((firstNode.getData() == null && data==null) ||
                (firstNode.getData() != null && firstNode.getData().equals(data))){
            Node oldFirstNode = firstNode;
            firstNode = firstNode.getNext();
            // 置空要删除的节点
            oldFirstNode.setData(null);
            oldFirstNode.setNext(null);
            oldFirstNode = null;
            size--;
            return true;
        }

        // 走到这里，说明firstNode不是要remove的元素
        Node pre = firstNode;
        Node current = firstNode.getNext();
        while(current != null){// 说明链表还有数据
            // 如果当前节点中维护的值就是要删除的值，直接将上一个节点的next
            // 应用指向当前节点current的下一个节点接口
            if((current.getData() == null && data==null)
                    || (current.getData() != null && current.getData().equals(data))){
                pre.setNext(current.getNext());
                // 置空要删除的节点
                current.setData(null);
                current.setNext(null);
                current = null;
                size--;
                return true;
            }
            // 如果当前元素不是要删除的元素，继续循环
            pre = current;
            current = current.getNext();
        }
        return false;
    }

    /**
     * 如果包含返回true，如果不包含，返回false
     */
    public boolean contains(Object data){
        if(firstNode == null){
            return false;
        }

        Node current = firstNode;
        do {
            if((current.getData() == null && data==null) ||
                    (current.getData() != null && current.getData().equals(data))){
                return true;
            }
            current = current.getNext();
        } while (current != null);

        return false;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    public void display(){
        if(!isEmpty()){
            Node current = firstNode;
            while(current!=null){
                System.out.print(current.getData() + "\t");
                current = current.getNext();
            }
        }
    }

    /**
     * 删除第一个元素
     */
    public T removeFirst(){
        if(size != 0){
            Node oldFirstNode = firstNode;
            firstNode = firstNode.getNext();
            size--;
            return (T) oldFirstNode.getData();
        }
        return null;
    }

    public T getFirst(){
        if(size() != 0){
            return firstNode.getData();
        }
        return null;
    }

    protected static class Node<E> {
        // Node 中维护的数据
        private E data;
        // 下一个元素的引用
        private Node<E> next;
        // 上一个元素的引用
        private Node<E> prev;

        Node() {}

        Node(Node<E> prev, E element, Node<E> next) {
            this.data = element;
            this.next = next;
            this.prev = prev;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    @Override
    public Iterator iterator() {
        // 把链表的firstNode当做构造参数传递给了NodeIterator
        return new NodeIterator<T>(firstNode);
    }

    /**
     * 在SingleLinkList中定义一个内部类NodeIterator，实现Iterator接口,
     * 这样我们定义的SingleLinkList就可以使用JDK的增强for循环了
     */
    private class NodeIterator<T> implements Iterator<T> {

        private Node node;

        public NodeIterator(Node current) {
            this.node = current;
        }

        @Override
        public boolean hasNext() {
            return node!=null;
        }

        @Override
        public T next() {
            Object data = node.getData();
            node=node.getNext();
            return (T)data ;
        }

        @Override
        public void remove() {
            T t = (T) node.getData();
            // 待完善
            SingleLinkList.this.remove(t);
        }

    }

}

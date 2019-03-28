package com.java.algorithm.linkedlist;

import com.java.algorithm.iterator.MyIterable;
import com.java.algorithm.iterator.MyIterator;

/**
 * 单端链表
 * 维护第一个节点(firstNode)的引用，作为整个链表的入口.
 * @param <T> 每个Node中存放的data的类型Type
 */
public class SingleLinkList<T> implements MyIterable<T> {

    // 链表中第一个节点
    protected Node<T> firstNode;

    // 链表中维护的节点总量
    protected int size;

    /**
     * 添加到链表的最前面
     * @param data Node中的data
     */
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
     * @param data
     * @return
     */
    public boolean remove(Object data) {
        if (size == 0) {
            firstNode = null;
            return false;
        }

        // 如果只有一个节点，就判断这个节点(也就是头结点)
        // 是否是要删除的元素
        if(size == 1){//size==1，所以firstNode肯定不为null
            // 判断一下第一个元素是否是要remove的元素
            if((firstNode.getData() == null && data==null) ||
                    (firstNode.getData() != null && firstNode.getData().equals(data))){
                firstNode = null;
                size--;
                return true;
            }
            return false;
        }

        // 走到这里，说明firstNode不是要remove的元素
        Node pre = firstNode;
        Node current = firstNode.getNext();
        while(current != null){
            // 如果当前节点中维护的值就是要删除的值，直接将上一个节点pre的next
            // 应用指向当前节点current的下一个节点接口
            if((current.getData() == null && data==null)
                    || (current.getData().equals(data))){
                pre.setNext(current.getNext());
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
     * @param data
     * @return
     */
    public boolean contains(Object data){
        if(size == 0){
            return false;
        }

        Node current = firstNode;
        while(current != null){
            if((current.getData() == null && data==null) ||
                    (current.getData() != null && current.getData().equals(data))){
                return true;
            }

            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public int size() {
        return size;
    }

    /**
     * 打印所有元素
     */
    public void display(){
        if(!isEmpty()){
            Node current = firstNode;
            while(current!=null){
                System.out.println(current.getData() + "\t");
                current = current.getNext();
            }
        }
    }

    /**
     * 删除第一个元素
     * @return
     */
    public T removeFirst(){
        if(size() != 0){
            Node oldFirstNode = firstNode;
            firstNode = firstNode.getNext();
            size--;
            return (T) oldFirstNode.getData();
        }
        return null;
    }

    public T getFirst(){
        if(size() != 0){
            return (T) firstNode.getData();
        }
        return null;
    }

    public static void main(String[] args) {
//        testAddFirst();
//
//        testRemove();
//
//        testRemoveFisrt();

        testIterator();
    }

    private static void testRemove() {
        System.out.println("---------");
        SingleLinkList<Integer> linkList=new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        if(!linkList.isEmpty()){
            linkList.remove(5);
        }
        linkList.display();
    }

    private static void testAddFirst() {
        SingleLinkList<Integer> linkList=new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        linkList.display();
    }

    public static void testRemoveFisrt() {
        System.out.println("---------------");
        SingleLinkList<Integer> linkList=new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        linkList.removeFirst();
        linkList.display();
    }

    public static void testIterator(){
        System.out.println("---------");
        SingleLinkList<Integer> linkList=new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        System.out.println("链表中元素：");
        linkList.display();
        System.out.println("\n开始迭代：");
        MyIterator<Integer> iterator = linkList.iterator();
        while (iterator.hasNext()){
            Integer next = iterator.next();
            System.out.println(next);
        }
    }

    public static void testIterator2(){
        SingleLinkList<Integer> linkList=new SingleLinkList<Integer>();
        for (int i = 0; i < 10; i++) {
            linkList.addFirst(i);
        }
        System.out.println("链表中元素：");
        linkList.display();
        System.out.println("\n开始迭代：");
        // 特别的，由于我们的迭代器实现了Java的标准接口，所以我们可以使用java的增强for循环来进行迭代，
        // 如果没有实现这些接口，是无法使用增强for循环的
        //使用增强for循环进行迭代
//        for (Integer data : linkList) {
//            System.out.println(data);
//        }
    }

    /**
     * 可以看到，我们构建的时候，是把链表的第一个元素当做构造参数传递给了NodeIterator
     * @return
     */
    @Override
    public MyIterator iterator() {
        return new NodeIterator<T>(firstNode);
    }

    /**
     * 在SingleLinkList中定义一个内部类NodeIterator，实现Iterator接口
     * @param <T>
     */
    private class NodeIterator<T> implements MyIterator<T> {

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

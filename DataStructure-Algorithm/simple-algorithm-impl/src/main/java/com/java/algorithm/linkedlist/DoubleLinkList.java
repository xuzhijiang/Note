package com.java.algorithm.linkedlist;

/**
 * 双端链表
 *
 * 我们可以像在单链表中在表头插入一个元素一样，在链表的尾端插入元素。
 * 如果不维护对最后一个节点的引用，我们必须要迭代整个链表才能得到最后一个节点，
 * 然后再插入，效率很低。因此我们在双链表中添加一个addLast方法，用于添加节点到末尾。
 * @param <T>
 */
public class DoubleLinkList<T> extends SingleLinkList<T> {

    // 链表中的最后一个节点
    protected Node<T> lastNode;

    /**
     * 添加到链表最后
     * @param data
     */
    public void addLast(T data){
        Node node = new Node();
        node.setData(data);

        if(size() == 0){// 说明没有元素
            firstNode = node;
        }else{// 如果有元素，将最后一个节点的next指向新的节点即可
//            这里有一个要注意的地方：
//            当size=1的时候，firstNode和lastNode指向同一个引用
//            因此lastNode.setNext时，fisrtNode的next引用也会改变;
//            当size!=1的时候，lastNode的next的改变与firstNode无关
            lastNode.setNext(node);
        }

        //将lastNode引用指向新node
        lastNode = node;
        size++;
    }

    /**
     * 当链表中没有元素时，清空lastNode引用
     * @param data
     * @return
     */
    @Override
    public boolean remove(Object data) {
        boolean result = super.remove(data);
        // 此方法还是有bug,因为SingleLinkList没有维护lastNode的信息
        if(size == 0){
            lastNode = null;
        }
        return result;
    }

    /**
     * 因为在SingleLinkList中并没有维护lastNode的信息，我们要自己维护
     */
    @Override
    public void addFirst(T element) {
        super.addFirst(element);
        if(size==1){//如果链表为size为1，将lastNode指向当前节点
            lastNode=firstNode;
        }
    }

    public static void main(String[] args) {
        testAddFirst();
    }
    public static void testAddFirst() {
        DoubleLinkList<Integer> linkList=new DoubleLinkList<Integer>();
        for (int i = 0; i < 5; i++) {
            linkList.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            linkList.addLast(i);
        }
        linkList.display();
    }
}

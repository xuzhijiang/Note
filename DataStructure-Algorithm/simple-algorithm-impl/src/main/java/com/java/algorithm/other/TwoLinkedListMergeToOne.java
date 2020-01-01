package com.java.algorithm.other;

// 把两个排好序的链表合并成一个
public class TwoLinkedListMergeToOne {
    public static void main(String[] args) {
        // 准备2个排好序的链表
        Node node1 = new Node(-1);
        Node node2 = new Node(-1);
        init(node1);
        init(node2);

        System.out.println("合并前打印node1*****************");
        print(node1.next);
        System.out.println();
        System.out.println();

        System.out.println("合并前打印node2*****************");
        print(node2.next);
        System.out.println();
        System.out.println();

        Node result = merge(node1.next, node2.next);


        System.out.println("合并后打印result*****************");
        print(result);
    }

    // 初始化Node
    public static void init(Node head) {
        Node current = head;
        for (int i = 0; i < 10; i++) {
            Node tmp = new Node(i);
            current.next = tmp;
            current = tmp;
        }
    }

    // 打印Node
    public static void print(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.value + ", ");
            current = current.next;
        }
    }

    // 合并Node
    public static Node merge(Node l1, Node l2) {
        Node head = new Node(-30);
        Node lastNode = head;

        while (l1 != null && l2 != null) {
            if (l1.value < l2.value) {
                lastNode.next = l1;
                lastNode = lastNode.next;
                l1 = l1.next;
            } else {
                lastNode.next = l2;
                lastNode = lastNode.next;
                l2 = l2.next;
            }
        }

        if (l1 == null) {
            lastNode.next = l2;
        }

        if (l2 == null) {
            lastNode.next = l1;
        }

        return head.next;
    }

    private static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

}

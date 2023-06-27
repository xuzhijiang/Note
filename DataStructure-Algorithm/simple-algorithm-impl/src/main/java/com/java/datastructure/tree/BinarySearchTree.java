package com.java.datastructure.tree;

import java.util.LinkedList;

// 二叉排序树: 二叉搜索树的中序遍历就是从小到大输出
public class BinarySearchTree {

    int data; // 表示数据
    BinarySearchTree left;
    BinarySearchTree right;

    public BinarySearchTree(int data) {
        this.data = data;
        left = null;
        right = null;
    }

    public void insert(BinarySearchTree root, int data) {
        if (data < root.data) { // 放在root的左子树
            if (root.left == null) { // 如果root没有左子树,直接插入
                root.left = new BinarySearchTree(data);
            } else {
                insert(root.left, data);
            }
        } else { // 放在root的右子树
            if (root.right == null) {
                root.right = new BinarySearchTree(data);
            } else {
                insert(root.right, data);
            }
        }
    }

    // 前序遍历
    public static void preOrder(BinarySearchTree root) {
        if (root != null) {
            System.out.print(root.data + ", ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    // 中序遍历 左根(输出)右, 二叉搜索树的中序遍历就是从小到大输出
    public static void inOrder(BinarySearchTree root) {
        if (root != null) {
            inOrder(root.left);
            System.out.print(root.data + ", ");
            inOrder(root.right);
        }
    }

    // 后序遍历
    public static void postOrder(BinarySearchTree root) {
        if (root != null) {
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data + ",");
        }
    }

    // 层序遍历
    public static void level(BinarySearchTree root) {
        LinkedList<BinarySearchTree> queue = new LinkedList<>();

        queue.offer(root); // 排队到队尾
        while (!queue.isEmpty()) {
            BinarySearchTree current = queue.poll(); // 队头的出队
            System.out.print(current.data + ", ");

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
    }

    public static void main(String[] args) {
        int data[] = {5, 9, 0, 1, 2, 3, 10, 4};
        // 序列的第一个作为root节点
        BinarySearchTree root = new BinarySearchTree(data[0]);
        for (int i = 1; i < data.length; i++) {
            root.insert(root, data[i]);
        }
        System.out.println("中序遍历为: ");
        BinarySearchTree.inOrder(root);

        System.out.println();
        System.out.println();
        System.out.println("前序遍历: ");
        BinarySearchTree.preOrder(root);

        System.out.println();
        System.out.println();
        System.out.println("后序遍历: ");
        BinarySearchTree.postOrder(root);


        System.out.println();
        System.out.println();
        System.out.println("层序遍历: ");
        BinarySearchTree.level(root);
    }
}

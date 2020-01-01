package com.java.datastructure.tree.redblack;

public class RedBlackTree {

    private final int R = 0; // 红色
    private final int B = 1; // 黑色

    private Node root = null; // 红黑树的根节点,每棵树都有一个根节点

    class Node {
        int data; // 存的具体数字
        int color = R; // 所有插入节点都是红色
        Node left;
        Node right;
        Node parent; // 如果是根节点,parent为null

        public Node(int data) {
            this.data = data;
        }
    }

    public Node initRoot(int data) {
        root = new Node(data);
        return root;
    }

    // 红黑树的插入过程
    public void insert(Node root, int data) { // 往root里面插入data,root不会为null
        if (root.data < data) { // 插入的数据比root大,所以要插入到root右边
            if (root.right == null) { // 如果root的右节点为空,就直接插入
                root.right = new Node(data);
            } else {
                insert(root.right, data);   // 接下来以root的右节点作为根,递归插入
            }
        } else { // 插入到左边
            if (root.left == null) { // 如果root的左节点为空
                root.left = new Node(data);
            } else {
                insert(root.left, data);
            }
        }
    }

    // 左旋
    public void leftRotate(Node node) { // 以哪个点作为左旋
        // 第一种情况: 表示传入的node是根节点(node没有父节点)
        if (node.parent == null) { // node此时就是root
            Node E = node;
            Node S = E.right;

            // 总体: E的right和parent的指针要变.
            E.right = S.left; // 第一步: 移动S的左子树变成E的右子树
            S.left.parent = E; // 把S的左子树的父亲变为E

            E.parent = S; // 第二步: 修改E的指针
            S.left = E;

            S.parent = null; // 第三步修改S的指针

        } else { // 如果node不是根节点,那么就要操作3个点
            if (node == node.parent.left) { // 如果node是左子树
                node.parent.left = node.right; //
            } else { // node是右子树
                node.parent.right = node.left;
            }

        }
    }

    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        Node root = redBlackTree.initRoot(12);
        redBlackTree.insert(root, 19);
    }
}

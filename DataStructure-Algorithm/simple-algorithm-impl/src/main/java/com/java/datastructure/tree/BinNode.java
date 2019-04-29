package com.java.datastructure.tree;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BinNode<T> {

    BinNode<T> parent, lChild, rChild;// 父亲，孩子

    T data;// data域

    // 在树中的一个特定元素,它也需要记录一些重要的指标,比如说在这里最最重要的指标
    //就是height 高度,那么我们也后面会看到 二叉树,尤其是基于二叉树实现的
    //各种二叉搜索树,将会有各种各样不同的指标,比如说对于红黑树而言
    //那么可能就会有所谓的颜色的区别,对于左式堆而言
    //也有所谓的npl指标,诸如此类地,所以也需要为它们留有余地
    int height;// 高度

    public BinNode(T e, BinNode parent){
        this.data = e;
        this.parent = parent;
    }

    /**
     * 作为左孩子插入新节点
     * @param e
     * @return
     */
    BinNode<T> insertAsLC(T e){
        return lChild = new BinNode<T>(e, this);
    }

    /**
     * 作为右孩子插入新节点
     * @param e
     * @return
     */
    BinNode<T> insertAsRC(T e) {
        return rChild = new BinNode<T>(e, this);
    }

    /**
     * 子树规模，包括它在内所有后代的总数,以其为根的子树规模
     * 统计子树规模的size()接口，必须遍历其中的所有节点,所以需要O(n),线性时间.
     * @return
     */
    int size(){
        int s = 1; // 计入本身
        if (lChild != null) {
            s += lChild.size(); // 递归计入左子树规模
        }
        if(rChild != null) {
            s += rChild.size();
        }
        return s;
    }

    /**
     * 返回当前节点在中序遍历意义下的直接后继,
     * 也就是在全树中不小于当前节点的最小的那个节点
     * 在当前节点拥有右后代的情况下,算法将首先进入到
     * 它所对应的右子树,然后在右子树中,沿着左侧分支不断地下行
     * 直到最终不能继续下行,而整个过程最终所抵达的那个节点
     * 就应该是当前节点的直接后继
     *
     * 时间复杂度不会超过全树的高度O(h)
     * @return
     */
    BinNode<T> succ(BinNode<T> x) {
        // 如果x存在右孩子，则"x的后继结点"为 "以其右孩子为根的子树的最小结点"。
        if (x.rChild != null)
            return min(x.rChild);

        //如果x没有右孩子,也就是。则有两种可能：
        //1.x是一个左孩子，则x的后继结点为它的父结点。
        //2.x是一个右孩子，则查找x的最低的父结点，并且该父结点要具有左孩子，
        // 找到的这个最低的父结点就是x的后继结点。
        BinNode<T> parent = x.parent;
        while (parent != null && parent.rChild == x){
            x = parent;
            parent = parent.parent;
        }
        return parent;
    }

    BinNode<T> predecessor(BinNode<T> x){
        //如果x结点的左子树不为空，则左子树中的最大值即为前驱结点
        if (x.lChild != null)
            return max(x.lChild);
        //如果x没有左孩子。则有两种可能：
        //1.x是一个右孩子，则x的前驱结点为它的父结点。
        //2.x是一个左孩子，则查找x的最低的父结点，并且该父结点要具有右孩子，找到的这个最低的父结点就是x的前驱结点。
        BinNode<T> parent = x.parent;
        while (parent != null && parent.lChild == x){
            x = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 二叉树层次遍历
     */
    void travLevel() {
        Queue<BinNode<T>> q = new ArrayBlockingQueue<BinNode<T>>(100);//引入辅助队列
        q.add(this);//根节点入列
        while(!q.isEmpty()){//队列在变空之前反复迭代
            BinNode<T> x = q.poll();//取出队首节点，并随即访问
            visit(x);
            if(x.hasLChild()){
                q.add(x.lChild);//左孩子入队
            }
            if(x.hasRChild()){
                q.add(x.rChild);// 右孩子入队
            }
        }
    }


//    void travPre(); // 子树先序遍历
//
//    void travIn(); //子树中序遍历
//
//    void travPost(); //子树后序遍历

    boolean hasRChild(){
        return rChild != null;
    }

    boolean hasLChild(){
        return lChild != null;
    }

    void visit(BinNode<T> x){
        if(x == null){
            System.out.println("data is null");
            return;
        }
        System.out.println(x.data);
    }

    public BinNode<T> min(BinNode<T> x){
        while (x.lChild != null){
            x = x.lChild;
        }
        return x;
    }

    public BinNode<T> max(BinNode<T> x){
        while (x.rChild != null){
            x = x.rChild;
        }
        return x;
    }
}
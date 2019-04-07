package com.java.algorithm.tree;

import java.util.Comparator;

public abstract class BST<T extends Comparable<T>> extends BinTree<T>{

    /**
     * 典型的尾递归，可以更改为迭代版.
     * 要么返回null，要么返回目标节点
     * @param e 目标关键码
     * @return
     */
    protected BinNode<T> search(T e) {
        // 从根节点启动查找
        BinNode<T> currentNode = root;

        // 当前节点为null或者当前节点的值就是我们要找的目标节点，那么直接返回，
        // 就不用进入while循环
        while(currentNode != null && !currentNode.data.equals(e)) {
            int result = currentNode.data.compareTo(e);
            // 查找的范围可以通过比较后确定是左子树还是右子树
            if(result > 0){
                currentNode = currentNode.lChild;
            }else{
                currentNode = currentNode.rChild;
            }
            // 每循环一次，当前的节点root都会下降一层，因此
            // 这个算法最坏的情况循环的次数也不会超过树的高度。
            // 这也是这个算法对应的时间复杂度O(h)
        }
        return currentNode;
    }


    /**
     * 时间消耗主要集中在search和updateHeightAbove()
     * 而且这2者在最坏的情况下都不会超过树的高度h,所以总体而言时间复杂度不会超过
     * O(h)
     * @param e
     * @return
     */
    boolean insert(T e) {
        // 从根节点启动查找
        BinNode<T> currentNode = root;

        BinNode<T> hot = null;// 记下当前非空节点,在成功查找到时返回命中节点的父节点
        // 失败的时候hot会是我们最后一个访问的真实节点.注意这个hot会在insert中用到
        // 所以要理解

        // 当前节点为null或者当前节点的值就是我们要找的目标节点，那么直接返回，
        // 就不用进入while循环
        while(currentNode != null && !currentNode.data.equals(e)) {
            int result = currentNode.data.compareTo(e);
            hot = currentNode;
            // 查找的范围可以通过比较后确定是左子树还是右子树
            if(result > 0){
                currentNode = currentNode.lChild;
            }else{
                currentNode = currentNode.rChild;
            }
            // 每循环一次，当前的节点root都会下降一层，因此
            // 这个算法最坏的情况循环的次数也不会超过树的高度。
            // 这也是这个算法对应的时间复杂度O(h)
        }

        if(currentNode == null){//既禁止雷同元素，所以在查找失败时才实施插入操作
            currentNode = new BinNode<T>(e, hot);//在x处创建新节点，以hot为父亲
            size++;
            // 更新全树的规模，更新x及其历代祖先的高度
            updateHeightAbove(currentNode);
        }
        return true;
        //return currentNode;//无论e是否存在于原树中，至此总有currentNode.data == e
    }


    /**
     * 时间消耗主要集中在search和updateHeightAbove()
     * 而且这2者在最坏的情况下都不会超过树的高度h,所以总体而言时间复杂度不会超过
     * O(h)
     * @param e
     * @return
     */
    boolean remove(T e) {
        // 从根节点启动查找,定位目标节点
        BinNode<T> currentNode = root;

        BinNode<T> hot = null;// 记下当前非空节点,在成功查找到时返回命中节点的父节点
        // 失败的时候hot会是我们最后一个访问的真实节点.注意这个hot会在insert中用到
        // 所以要理解

        // 当前节点为null或者当前节点的值就是我们要找的目标节点，那么直接返回，
        // 就不用进入while循环
        while(currentNode != null && !currentNode.data.equals(e)) {
            int result = currentNode.data.compareTo(e);
            hot = currentNode;
            // 查找的范围可以通过比较后确定是左子树还是右子树
            if(result > 0){
                currentNode = currentNode.lChild;
            }else{
                currentNode = currentNode.rChild;
            }
            // 每循环一次，当前的节点root都会下降一层，因此
            // 这个算法最坏的情况循环的次数也不会超过树的高度。
            // 这也是这个算法对应的时间复杂度O(h)
        }

        // 确认目标节点存在，此时hot为currentNode的父亲
        if(currentNode == null){
            return false;
        }

        // 分2大类情况实施删除
        removeAt(currentNode, hot);
        // 更新全树的规模
        size--;
        // 更新hot以及其历代祖先的高度
        updateHeightAbove(hot);
        return true;//删除成功与否
    }

    BinNode<T> removeAt(BinNode<T> currentNode, BinNode<T> hot) {
        BinNode<T> w = currentNode;//实际被删除的节点
        BinNode<T> succ = null;// 实际被删除的节点的接替者

        if(!currentNode.hasLChild()){// 左子树为kong
            succ = currentNode.rChild;
        } else if(!currentNode.hasRChild()) {// 右子树为空
            succ = currentNode.lChild;
        } else {
            // 左右子树并存的情况

        }

        hot = w.parent;//实际被删除节点的父亲
        if(succ !=null){
            succ.parent = hot;//将被删除节点的接替者与hot关联
        }
        w.parent = null;//释放被删除节点
        return succ;
    }


}

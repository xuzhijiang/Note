package com.java.datastructure.tree;

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
        // 从它开始不断地向上追溯历代的祖先, 因为这些祖先的高度有可能
        // 因为刚才那个后代的删除,而发生变化
        updateHeightAbove(hot);
        return true;//删除成功与否
    }

    /**
     * 这个removeAt算法它的时间复杂度又是多少呢？是否会超过remove原有的O(h)那个复杂度呢？
     * 幸好不会原因在于removeAt本身并不包含任何循环
     * 而其中唯一可能引起复杂度的无非是在第二种情况下
     * 对succ()接口的调用而按照我们此前的实现方法以及分析结论
     * 这个接口所需要的时间也不会超过全树的高度O(h)
     * 至此我们可以得出一个结论BST的删除操作
     * 与插入操作一样，在最坏情况下所需要的时间
     * 不会超过全树当时的高度h
     * @param currentNode
     * @param hot
     * @return
     */
    BinNode<T> removeAt(BinNode<T> currentNode, BinNode<T> hot) {
        BinNode<T> w = currentNode;//实际被删除的节点
        BinNode<T> succ = null;// 实际被删除的节点的接替者

        if(!currentNode.hasLChild()){// 左子树为kong
            succ = currentNode.rChild;
        } else if(!currentNode.hasRChild()) {// 右子树为空
            succ = currentNode.lChild;
        } else {
            // 左右子树并存的情况
            w = currentNode.succ(currentNode);// 找到currentNode的直接后继
            //令当前这个节点与它的直接后继节点互相数据
            // 使待删除的节点转移到一个新的位置，而且这个位置只有一个右分支.左子树为kong
            swap(w, currentNode);
            BinNode<T> u = w.parent;// 原问题转化为了,摘除非2度的节点w
            // 因为此时这个w节点只有右孩子
            // 为什么它不会有左孩子呢？因为作为此前那个节点的直接后继,
            //它必然是某条左侧分支的末端,作为这个左侧分支的末端
            // 既然如此,我们就可以直接沿用刚才,对情况一的处理手法
            // 也就是直接令它唯一不空的那个孩子,去顶替它
            // 这棵树已经恢复成为了一棵,不折不扣的BST
            succ = w.rChild;

            // 我们只需要找出当前节点的直接后继, 并且令这两个节点的数据域互换
            // 从而等效地将待删除的节点, 转移至一个新的位置
            // ,而且这个位置至多只有一个分支,当然这个分支只可能是右孩子
            // 为了将待删除的节点,替换为它的这个右孩子
            // 我们只需在这个右孩子以及它此前的祖父之间，正确地完成一次双向连接
            if(u == currentNode){// 把右孩子和它的祖父之间完成一次正确的双向连接
                u.rChild = succ;
            }else{
                u.lChild = succ;
            }
        }

        // 我们还需要令内部的hot变量指向刚刚被实际删除的这个节点的父亲
        hot = w.parent;//实际被删除节点的父亲
        if(succ !=null){
            succ.parent = hot;//将被删除节点的接替者与hot关联
        }
        w.parent = null;//释放被删除节点
        return succ;
    }

    /**
     * 注意: 只是交换数据
     * @param w
     * @param currentNode
     */
    void swap(BinNode<T> w, BinNode<T> currentNode) {
        T tmp = w.data;
        w.data = currentNode.data;
        currentNode.data = tmp;
    }


}

package com.java.datastructure.tree;

import java.util.Stack;

/**
 * 二叉树是半线性结构
 * @param <T>
 */
public abstract class BinTree<T> {

    // 当前树中的节点总数
    int size;

    // 树跟节点的位置
    BinNode<T> root;

    /**
     * 更新节点x的高度，具体规则因树不同而异，所以次方法是抽象的.
     * x节点的高度为左右子树高度的max值
     * @param x
     * @return
     */
    abstract int updateHeight(BinNode<T> x);

    /**
     * 更新x及祖先的高度
     * 复杂度为x节点的深度
     * @param x
     * @return
     */
    int updateHeightAbove(BinNode<T> x) {
        return -1;
    }

    /**
     * 规模
     * @return
     */
    int size() {
        return size;
    }

    /**
     * 判空
     * @return
     */
    boolean empty() {
        return root == null;
    }

    /**
     * 树根
     * @return
     */
    BinNode<T> root() {
        return root;
    }


    /**
     * 节点插入
     * @param x
     * @param e
     * @return
     */
    BinNode<T> insertAsRC(BinNode<T> x, T e) {
        size++;
        x.insertAsRC(e);
        updateHeightAbove(x);
        return x.rChild;
    }

    /**
     * 先序遍历的递归实现
     * 对这个算法效率不满意
     * @param x
     */
    void traversePreRecursive(BinNode<T> x) {
        if(x == null) return;
        visit(x);
        traversePreRecursive(x.lChild);
        traversePostRecursive(x.rChild);
    }

    void visit(BinNode<T> x){
        if(x == null){
            System.out.println("data is null");
            return;
        }
        System.out.println(x.data);
    }

    /**
     * 先序遍历迭代实现的第一个版本
     * 这种算法推广不到后序和中序遍历
     * @param x
     */
    void traversePre_I1(BinNode<T> x){
        Stack<BinNode<T>> s = new Stack<BinNode<T>>();//辅助栈
        if(x!=null) s.push(x);// 根节点入栈
        while(!s.empty()){//在栈变得空之前反复入栈
            // 每个节点在弹出栈的时刻才被访问
            x = s.pop();
            // 弹出并访问当前节点
            visit(x);
            if(x.hasRChild()){
                // 右孩子先入后出
                s.push(x.rChild);
            }
            if(x.hasLChild()){
                // 左孩子后入先出
                s.push(x.lChild);
            }
        }
    }

    /**
     * 先序遍历迭代实现的第2个版本
     * 这种算法可以推广到后序和中序遍历
     * @param x
     */
    void traversePre_I2(BinNode<T> x){
        Stack<BinNode<T>> s = new Stack<BinNode<T>>();//辅助栈
       while(true){//以(右)子树为单位,逐批访问节点
           visitAlongLeftBranch(x, s);//访问子树x的左侧链，右子树入栈缓冲
           if(s.empty()) break;//栈空就退出
           x = s.pop();//弹出下一棵(右)子树的根
       }
    }

    /**
     * 右子树上的节点正好组成了一个stack，最上层的右子树节点在栈的bottom
     * ，最下层的右子树节点在栈的top
     * @param x
     * @param s
     */
    void visitAlongLeftBranch(BinNode<T> x, Stack<BinNode<T>> s){
        while(x!=null){//反复的
            visit(x);// 访问当前节点
            s.push(x.rChild);//右孩子(右子树)入栈(将来逆序出栈)
            x = x.lChild;//沿左侧链下行
        }//只有右孩子，null可能入栈，增加判断以剔除后者，是否值得?
    }

    /**
     * 中序遍历的递归实现
     * 对这个算法效率不满意
     * 对这种改进在实际意义中是非常大的，改成迭代的形式
     * @param x
     */
    void traverseInRecursive(BinNode<T> x) {
        if(x == null) return;
        traversePreRecursive(x.lChild);
        visit(x);
        traversePostRecursive(x.rChild);
    }

    void goAlongLeftBranch(BinNode<T> x, Stack<BinNode<T>> s){
        while(x != null){
            // push次数之和为n，每个节点至多一次入栈
            s.push(x);//根节点反复入栈
            x = x.lChild;//沿左侧分支深入
        }
    }

    /**
     * 中序遍历的迭代实现
     * 时间复杂度n的2次方为假象,
     * 需要的时间实际依然是线性的O(n),
     * 这里虽然比递归复杂，但是远胜于递归的版本.
     * 这里用到分摊分析
     * @param x
     */
    void travIn_I1(BinNode<T> x){
        Stack<BinNode<T>> s = new Stack<BinNode<T>>();//辅助栈
        // 外循环总共需要执行O(n),因为必须对每一个节点字少访问一次.
        while(true){//以反复的
            // 内循环，这个内循环迭代次数或多或少,内循环总共push了n次
            goAlongLeftBranch(x, s);//从当前节点出发,逐批入栈
            if(s.empty()) break;//直道所有节点处理完毕
            // 因为push总共n次，所以pop也总共n次,所以时间复杂度为0(N)
            x = s.pop();//x的左子树或为空，或已遍历(等价于空),所以可以
            visit(x);//立即访问之
            x = x.rChild;//再转向其右子树
        }
    }

    void traversePostRecursive(BinNode<T> x) {

    }


}

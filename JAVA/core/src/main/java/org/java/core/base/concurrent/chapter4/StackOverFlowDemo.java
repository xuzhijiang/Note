package org.java.core.base.concurrent.chapter4;

public class StackOverFlowDemo {
    int count = 0;

    /**
     * 我们定义了一个递归方法recursiveMethod()，
     * 我们希望递归方法执行了1百万次之后结束
     *
     * 可以看到，事实上递归了6765次的后，就抛出了StackOverflowError异常。
     * 这提示我们，在Java开发过程中，要慎用递归，尤其是在"不能预估递归方法大概需要执行多少次"的时候，
     * 最好就不好使用。
     */
    public void recursiveMethod() {
        if(count == 1000000){//递归方法执行1000000次时，结束
            return;
        }

        count++;
        System.out.println("执行了: " + count + "次");
        recursiveMethod();
    }

    public static void main(String[] args){
        StackOverFlowDemo stackOverFlowDemo = new StackOverFlowDemo();
        stackOverFlowDemo.recursiveMethod();
        System.out.println("执行其他代码");
    }
}

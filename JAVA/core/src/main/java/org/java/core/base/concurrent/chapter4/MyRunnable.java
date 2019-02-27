package org.java.core.base.concurrent.chapter4;

public class MyRunnable implements Runnable{
    public void run() {
        methodOne();
    }

    /**
     * methodOne()声明了一个原始类型的本地变量
     * 和一个引用类型的本地变量
     *
     * 每个线程执行methodOne()都会在它们对应的线程栈上
     * 创建localVariable1和localVariable2的私有拷贝。
     *
     * localVariable1变量彼此完全独立，仅“生活”在每个线程的线程栈上。
     * 一个线程看不到另一个线程对它的localVariable1私有拷贝做出的修改。
     *
     * 每个线程执行methodOne()时也将会创建它们各自的localVariable2拷贝。
     * 然而，两个localVariable2的不同拷贝都"指向堆上的同一个对象"。
     * 代码中通过一个静态变量设置localVariable2指向一个对象引用。
     * 仅存在一个静态变量的一份拷贝，这份拷贝存放在堆上。
     * 因此，localVariable2的两份拷贝都指向由MySharedObject指向的静态变量
     * 的同一个实例。MySharedObject实例也存放在堆上。它对应于上图中的Object3。
     */
    public void methodOne() {
        int localVariable1 = 45;

        MySharedObject localVariable2 =
                MySharedObject.sharedInstance;

        //... do more with local variables.

        methodTwo();
    }

    /**
     * 注意，methodTwo()创建一个名为localVariable的本地变量。
     * 这个成员变量是一个指向一个Integer对象的对象引用。
     * 这个方法设置localVariable1引用指向一个新的Integer实例。
     * 在执行methodTwo方法时，localVariable1引用将会在每个线程中存放一份拷贝。
     * 这两个Integer对象实例化将会被存储堆上，但是每次执行这个方法时，
     * 这个方法都会创建一个新的Integer对象，两个线程执行这个方法将会创建
     * 两个不同的Integer实例。methodTwo方法创建的Integer对象对应于上图中的Object1和Object5。
     */
    public void methodTwo() {
        Integer localVariable1 = new Integer(99);

        //... do more with local variable.
    }
}

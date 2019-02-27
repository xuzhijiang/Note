package org.java.core.base.concurrent.chapter4;

/**
 * MySharedObject类也包含两个成员变量。
 * 这些成员变量随着这个对象存放在堆上。
 * 这两个成员变量指向另外两个Integer对象。这些Integer对象对应
 * 于上图中的Object2和Object4.
 */
public class MySharedObject {
    // static variable pointing to instance of MyShardObject
    // 指向MySharedObject实例的静态变量
    public static final MySharedObject sharedInstance = new MySharedObject();

    // member variables pointing to two objects on the heap
    // 指向两个对象的成员变量,这俩对象在堆上

    public Integer object2 = new Integer(22);
    public Integer object4 = new Integer(44);

    // 还有一点，MySharedObject类中的两个long类型的成员变量是原始类型
    // 的。因为，这些变量是成员变量，所以它们任然随着该对象存放在堆上
    // ，仅有本地变量存放在线程栈上。
    public long member1 = 12345L;
    public long member2 = 49798L;
}

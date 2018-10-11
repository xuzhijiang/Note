package org.java.core.base.generics;

/**
 * Java中的泛型在编译时提供类型检查，它在运行时没有用
 * 
 *  因此java编译器使用类型擦除功能删除  字节代码中的所有泛型类型检查代码，
 *  并在必要时插入类型转换。 
 *  
 *  类型擦除确保不为 参数化类型 创建新类; 因此，泛型不会产生运行时开销。
 *
 * @param <T>
 */
public class Test<T extends Comparable<T>> {

    private T data;
    private Test<T> next;

    public Test(T d, Test<T> n) {
        this.data = d;
        this.next = n;
    }

    public T getData() { return this.data; }
}


// Java编译器用第一个绑定接口Comparable替换有界类型参数T, as below code:
//public class Test {
//
//    private Comparable data;
//    private Test next;
//
//    public Node(Comparable d, Test n) {
//        this.data = d;
//        this.next = n;
//    }
//
//    public Comparable getData() { return data; }
//}

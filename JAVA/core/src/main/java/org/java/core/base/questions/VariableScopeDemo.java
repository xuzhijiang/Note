package org.java.core.base.questions;

/**
 * 变量的分类
 *
 * 局部变量作用域: 从声明处开始，到所属的}结束
 * 类变量：随着类的初始化而初始化，随着类的卸载而消亡，该类的所有对象的类变量是共享的
 * 实例变量：随着对象的创建而初始化，随着对象的被回收而消亡，每一个对象的实例变量是独立的
 */
public class VariableScopeDemo {
    static int s;
    int i;
    int j;

    {
        int i = 1; //非静态代码块中的局部变量 i
        i++; // 就近原则
        j++;
        s++;
    }

    public void test(int j) { //形参，局部变量,j
        j++; // 就近原则,所以这里的j是局部变量
        i++;
        s++;
    }

    public static void main(String[] args) { //形参，局部变量，args
        VariableScopeDemo o1 = new VariableScopeDemo(); //局部变量，o1
        VariableScopeDemo o2 = new VariableScopeDemo(); //局部变量，o2
        o1.test(10);
        o1.test(20);
        o2.test(30);
        System.out.println(o1.i + ", " + o1.j + ", " + o1.s);
        System.out.println(o2.i + ", " + o2.j + ", " + o2.s);
    }
}

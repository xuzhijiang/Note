package org.java.core.base.questions;

/**
 * 1. 赋值=，最后计算
 * 2. =右边的从左到右加载值依次压入操作数栈
 * 3. 实际先算哪个，看运算符优先级
 * 4. 自增、自减操作都是直接修改变量的值，不经过操作数栈
 * 5. 最后的赋值之前，临时结果也是存储在操作数栈中
 *
 *
 * 《JVM虚拟机规范》关于指令的部分
 */
public class AssignValueQuestion {
    public static void main(String[] args) {
        int i = 1;
        // 赋值= 是最后计算的,所以先计算右边的 i++
        // i++: 第一步是把i的值压入操作数栈
        // 第二步:i变量自增1,自增、自减操作都是直接修改变量的值，不经过操作数栈,所以局部变量表中i的值已经变为2
        // 第三步: 把操作数栈中的值赋值给i, 所以 i = i++; 这行走完后,i变为1
        i = i++;
        int j = i++;
        int k = i + ++i * i++;
        System.out.println("i=" + i);
        System.out.println("j=" + j);
        System.out.println("k=" + k);
    }

}

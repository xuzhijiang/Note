package com.java.algorithm.math;

// 用递归的方式解决台阶问题
// 递归优点：可读性好；
// 缺点：递归调用浪费了空间，而且递归太深容易造成栈的溢出
public class StepQuestion01 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(5));
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + "毫秒");
    }

    // 实现f(n)：求n步台阶，一共有几种走法
    public static int f(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("台阶数n不能小于1");
        }

        if (n == 1 || n == 2) {
            return n;
        }

        return f(n-2) + f(n - 1);
    }
}

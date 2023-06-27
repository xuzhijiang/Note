package com.java.algorithm.math;

// 迭代优点：代码运行效率好，因为时间只随循环次数增加而增加，而且没有额外的空间开销；
// 缺点：代码不如递归可读性好
public class StepQuestion02 {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(f(4));
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end - start) + "毫秒");
    }

    public static int f(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("台阶数n不能小于1");
        }
        if (n == 1 || n == 2) {
            return n;
        }

        int sum = 0;
        int one = 1; // 走到第1级台阶的走法
        int two = 2; // 走到第2级台阶的走法

        for (int i = 3; i <= n; i++) {
            //最后跨2步 + 最后跨1步的走法
            sum = one + two;
            one = two;
            two = sum;
        }

        return sum;
    }
}

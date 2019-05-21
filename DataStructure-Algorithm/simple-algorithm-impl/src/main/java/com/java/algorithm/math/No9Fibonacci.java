package com.java.algorithm.math;

import javax.security.sasl.SaslServer;

/**
 * 题目：写一个函数，输入n，求斐波那契数列的第n项。(n>=0)
 *
 *                              0,              n=0
 * 斐波那契数列定义如下：f(n)=  1,              n=1
 *                              f(n-1)+f(n-2)   n>=2
 */
public class No9Fibonacci {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //System.out.println(generateFibonacciByRecursive(40));
        // System.out.println(generateFibonacciByCycle(50));// 注意int的范围，输入50会超int的范围.
//        System.out.println(generateFibonacciByCycle(40));
        System.out.println(generateFibonacciByRecursive(40));
        System.out.println("耗时: " + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

    /*
     * 递归实现，效率低
     * n >= 0
     */
    static int generateFibonacciByRecursive(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return generateFibonacciByRecursive(n-1) + generateFibonacciByRecursive(n-2);
    }

    /**
     * 采用循环实现(非递归)
     * 存储数列中间项，求得结果
     * @param n n >= 0
     */
    static int generateFibonacciByCycle(int n) {
        int[] result = {0, 1};
        if (n < 2)
            return result[n];
        int x = 0;
        int y = 1;
        int sum = 0;
        for (int i=2;i<=n;i++){
            System.out.println("x: " + x);
            System.out.println("y: " + y);
            sum = x + y;
            x = y;
            y = sum;
        }
        return sum;
    }

}

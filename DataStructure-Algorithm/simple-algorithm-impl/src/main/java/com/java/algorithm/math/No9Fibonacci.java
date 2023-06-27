package com.java.algorithm.math;

/**
 *                              0,              n=0
 * 斐波那契数列定义如下：f(n)=  1,              n=1
 *                              f(n-1)+f(n-2)   n>=2
 */
public class No9Fibonacci {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // System.out.println(generateFibonacciByCycle(50));// 注意int的范围，输入50会超int的范围.
//        System.out.println("result: " + generateFibonacciByRecursive(45));
        System.out.println(generateFibonacciByCycle(45));
        System.out.println(fib2(45, 0, 1));
        System.out.println("耗时: " + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

    /*
     * 递归简化了问题的表达,但牺牲了内存空间.因为每次调用自身的时候,都会在内存中开辟新的空间(在栈中)
     */
    static int generateFibonacciByRecursive(int n) {
        if (n < 2)
            return n;
        return generateFibonacciByRecursive(n-1) + generateFibonacciByRecursive(n-2);
    }

    /**
     * 非递归的方式
     */
    static long generateFibonacciByCycle(int n) {
        if (n <= 1) return n;

        long fib0 = 0;
        long fib1 = 1;
        long result = 0;
        for (long i = 2; i <= n; i++){
            // 计算出f(n) = f(n-1) + f(n-2)
            result = fib0 + fib1;
            // f(n-2)
            fib0 = fib1;
            // f(n-1)
            fib1 = result;
        }
        return result;
    }

    /**
     * 尾递归优化思想
     * @param count
     * @param beforePrev f(n-2)
     * @param prev f(n-1)
     * @return
     */
    public static long fib2(long count, long beforePrev, long prev) {
        if (count == 1)
            return prev;
        else {
            // 注意是直接返回方法,这样在递归调用时,新方法会覆盖当前栈帧,达到节省栈空间的目的.
            // 因此也就不会有递归调用产生的栈溢出问题.
            return fib2(--count, prev, prev + beforePrev);
        }
    }
}

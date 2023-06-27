package com.java.algorithm.search.BinarySearch;

/**
 * 计算平方根,注意计算结果要保留我们需要的小数点位数
 *
 * 面试的时候,要说y=x^2这个函数是单调递增的.所以可以用二分查找.
 */
public class SqrtCompute {

    public static void main(String[] args) {
        double remain = 1e-9;
        double result = mySqrt(3, remain);
        System.out.println("******result: " + result);
        System.out.println(Math.floor(result * 1e9) / 1e9);

        double standard = Math.sqrt(3); // 标准库
        System.out.println("*********standard: " + standard);
    }

    // remain: 精度,表示小数点后精确到多少位
    // 就是求出x平方根的浮点数,而且尽量精确.
    public static double mySqrt(int x, double remain) {
        if (x == 0 || x == 1)
            return x;

        double left = 0;
        double right = x;
        double result =  0;

        while (left <= right) {
            double mid = (left + right) / 2;
//            if (Math.abs(x - mid*mid) < remain) {
            if (Math.abs(x/mid - mid) < remain) {
                // 最后终止的条件就是 x 和 mid*mid 之间的差要小于1e-9(这个精度是自己传入的.)
                return mid;
            } else if (mid < x/mid) {
                // left和right都是浮点数,所以不能进行+1 或者 -1的操作,直接用mid本身即可.
                left = mid;
            } else if (mid > x/mid) {
                // left和right都是浮点数,所以不能进行+1 或者 -1的操作,直接用mid本身即可.
                right = mid;
            }
        }
        return result;
    }

}

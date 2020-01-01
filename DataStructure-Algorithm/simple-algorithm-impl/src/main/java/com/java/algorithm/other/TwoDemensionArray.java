package com.java.algorithm.other;

/**
 * 在二维数组中判断是否存在要查找的数字
 */
public class TwoDemensionArray {

    public static boolean exists(int[][] array, int num) {
        int row = array.length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (num == array[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] array = new int[][]{
                {1, 3, 100, 89},
                {2, 82, 29, 30},
                {22, 11, 28, 39}
        };

        System.out.println("是否存在: " + exists(array, 29));
        System.out.println("是否存在: " + exists(array, 100));
        System.out.println("是否存在: " + exists(array, 200));
    }
}

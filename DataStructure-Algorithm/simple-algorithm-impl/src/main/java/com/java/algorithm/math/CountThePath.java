package com.java.algorithm.math;

/**
 * 使用递推的方式解决
 */
public class CountThePath {

    /**
     * 自下而上 递推 计算
     */
    private int countPaths(boolean[][] grid, int row, int col) {
        // 声明状态变量，用于记忆。这里是二维棋盘，所以用二维数组存储
        int[][] opt = new int[grid.length][grid[0].length]; // 状态的定义

        for (int i = grid.length - 1; i >= 0 ; i--) {
            for (int j = grid[0].length - 1; j >= 0 ; j--) {
                System.out.println(i + "," + j);
                if (i == (grid.length - 1) || j == (grid[0].length - 1)) {
                    // 直接给 最后一行 和 最后一列 赋值
                    opt[i][j] = 1;
                } else if (grid[i][j]) { // 如果这个位置是空地，则说明可以行走
                    opt[i][j] = opt[i+1][j] + opt[i][j+1]; // 递推公式 (状态转移方程)
                } else {  // 石头
                    opt[i][j] = 0;
                }
            }
        }

        return opt[row][col];
    }

    public static void main(String[] args) {
        // 初始化棋盘, true表示空地, false表示石头
        boolean[][] grid  = new boolean[][] {
                {true, true, true, true, true, true, true, true},
                {true, true, false, true, true, true, false, true},
                {true, true, true, true, false, true, true, true},
                {false, true, false, true, true, false, true, true},
                {true, true, false, true, true, true, true, true},
                {true, true, true, false, false, true, false, true},
                {true, false, true, true, true, false, true, true},
                {true, true, true, true, true, true, true, true}
        };

        int result = new CountThePath().countPaths(grid, 0, 0);
        System.out.println("result: " + result);
    }

}

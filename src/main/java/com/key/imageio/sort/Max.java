package com.key.imageio.sort;

import java.util.Random;

/**
 * 动态规划算法示例
 *
 * @author tujia
 * @since 2020/7/13 15:59
 */
public class Max {
    public static void main(String[] args) {
        int[][] nn = new int[6][6];
        Random random = new Random(1);
        for (int i = 0; i <= 5; i++) {
            for (int j = 0; j < i; j++) {
                nn[i][j] = random.nextInt(10);
                System.out.println(nn[i][j]);
            }
        }
        int sum = max(nn);
        int num = getMaxSum(nn, 5, 0, 0);
        System.out.println(sum);
        System.out.println(num);
    }

    /**
     * 动态规划算法
     *
     * @param num
     * @return
     */
    private static int max(int[][] num) {
        int[] maxSum = new int[7];
        System.out.println("-----------------------");
        for (int i = num.length - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                maxSum[j] = Math.max(maxSum[j], maxSum[j + 1]) + num[i][j];
                System.out.println(maxSum[j]);
            }
        }
        System.out.println("-----------------------");
        for (int i = 0; i < maxSum.length; i++) {
            System.out.println(maxSum[i]);
        }
        return maxSum[0];

    }

    /**
     * 递归调用算法
     *
     * @param num
     * @param n
     * @param i
     * @param j
     * @return
     */
    private static int getMaxSum(int[][] num, int n, int i, int j) {
        if (i == n) {
            return num[i][j];
        }

        int x = getMaxSum(num, n, i + 1, j);
        int y = getMaxSum(num, n, i + 1, j + 1);
        return Math.max(x, y) + num[i][j];
    }
}

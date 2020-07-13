package com.key.imageio.sort;

/**
 * 冒泡排序
 * 算法描述：
 * 1. 双重循环，每次遍历比较相邻元素大小
 * 2. 如果后者比前者小，则交换位置元素
 * 时间复杂度：
 * @author tujia
 * @since 2020/7/13 9:45
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        int[] num = bubbleSort(a);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }

    private static int[] bubbleSort(int[] num) {
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num.length - 1 - i; j++) {
                if (num[j] < num[j + 1]) {
                    int temp = num[j + 1];
                    num[j + 1] = num[j];
                    num[j] = temp;
                }
            }
        }

        return num;
    }
}

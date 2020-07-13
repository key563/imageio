package com.key.imageio.sort;

/**
 * 选择排序
 * 算法描述：
 *  1.双重循环，每次从当前位置到末尾遍历，找到最小的元素，
 *  2.如果最小的元素不是当前位置，则交换位置元素
 *
 * 时间复杂度：O(n^2)，稳定
 * @author tujia
 * @since 2020/7/13 9:35
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        int[] num = selectionSort(a);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }

    private static int[] selectionSort(int[] num) {
        for (int i = 0; i < num.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < num.length; j++) {
                if (num[minIndex] > num[j]) {
                    minIndex = j;
                }

            }
            if (minIndex != i) {
                int tmp = num[i];
                num[i] = num[minIndex];
                num[minIndex] = tmp;
            }
        }
        return num;
    }
}

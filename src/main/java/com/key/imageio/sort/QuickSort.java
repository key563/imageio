package com.key.imageio.sort;

/**
 * 快速排序
 *
 * @author tujia
 * @since 2020/7/13 12:06
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        quickSort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }

    private static void quickSort(int[] num, int low, int high) {

        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int key = num[low];

        while (i < j) {
            // 从右往左找到第一个小于key的数
            while (i < j && num[j] > key) {
                j--;
            }
            // 从左往右找到第一个大于key的数
            while (i < j && num[i] <= key) {
                i++;
            }
            if (i < j) {
                int tt = num[j];
                num[j] = num[i];
                num[i] = tt;
            }

        }
        // 调整key的位置
        int temp = num[i];
        num[i] = num[low];
        num[low] = temp;
        // 对左边进行递归排序
        quickSort(num, low, i - 1);
        // 对右边进行递归排序
        quickSort(num, i + 1, high);
    }

    private static void test(int[] a, int low, int high) {
        if (low > high) {
            return;
        }

        int i = low;
        int j = high;
        int key = a[low];

        while (i < j) {
            while (i < j && a[j] > key) {
                j--;
            }

            while (i < j && a[i] <= key) {
                i++;
            }

            // 交换位置
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        // 在交换位置
        int tt = a[i];
        a[i] = a[low];
        a[low] = tt;

        test(a, low, i - 1);
        test(a, i + 1, high);
    }
}

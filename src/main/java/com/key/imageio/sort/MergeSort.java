package com.key.imageio.sort;

/**
 * 归并排序--2-路归并
 * 1. 通过递归的思路，将数组一分为二，每个小数组再一分为二，直到每个小数组只有一个/两个元素时
 * 2. 通过一个临时数组，分别比较每次一分为二的数组的两边的每个元素，每次将最小的元素放入临时数组，直到有个小数组已遍历完成，然后将另外一个未遍历完的数组元素直接转移到临时数组
 * 3. 将临时数组对应位置转移到原数组上
 *
 * 时间复杂度：O(N*log2N)
 * @author tujia
 * @since 2020/7/13 10:23
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        int[] num = mergeSort(a, 0, a.length - 1);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }

    private static int[] mergeSort(int[] a, int min, int high) {
        int mid = (min + high) / 2;
        if (min < high) {
            mergeSort(a, min, mid);
            mergeSort(a, mid + 1, high);
            merge(a, min, mid, high);
        }
        return a;
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数移动到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移动到数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }

        // 把右边剩余的数移动到数组
        while (j <= high) {
            temp[k++] = a[j++];
        }

        // 将新数组覆盖到原数组的对应位置
        for (int i1 = 0; i1 < temp.length; i1++) {
            a[i1 + low] = temp[i1];
        }

    }
}

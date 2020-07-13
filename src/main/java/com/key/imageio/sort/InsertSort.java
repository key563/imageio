package com.key.imageio.sort;

/**
 * 插入排序
 * 算法描述：
 * 1.假设从第一个元素开始已经排好序
 * 2.从第二个元素开始，从已排序的元素中遍历，如果当前元素大于新元素，则需要将当前元素及其后面的，都向后移动一位
 *
 * @author tujia
 * @since 2020/7/13 9:57
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        int[] num = insertSort(a);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }
    private static int[] insertSort(int[] num) {
        for (int i = 1; i < num.length; i++) {
            int pre = i - 1;
            int cur = num[i];
            while (pre >= 0 && num[pre] > cur) {
                num[pre + 1] = num[pre];
                pre--;
            }
            num[pre+1] = cur;

        }
        return num;
    }
}

package com.key.imageio.sort;

/**
 * 堆排序
 * 算法描述：
 * 1. 利用堆结构，类似二叉树，在遍历和排序时按照对应结构进行排序
 * 2. 将待排序的序列构造成一个大顶堆/小顶堆，即按层是相对有序的，父节点始终大于/小于 子节点
 * 3. 再将大顶堆的最顶上元素与数组队尾元素交换，即最大元素排好序了，队尾-1，再重新构建大顶堆，依次取出大顶堆最顶上元素与队尾交换
 *
 * 时间复杂度：O(N*log2N)
 * @author tujia
 * @since 2020/7/13 10:40
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] a = {3, 2, 7, 4, 8, 6, 1, 9};
        int[] num = heapSort(a);
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }

    private static int[] heapSort(int[] num) {

        // 1. 构建大顶堆
        for (int i = num.length / 2 - 1; i >= 0; i--) {
            // 从第一个非叶子节点从下至上构建，从右至左调整
            adjustHeap(num, i, num.length);
        }

        // 2.调整堆结构
        for (int j = num.length - 1; j > 0; j--) {
            // 先交换堆顶元素和末尾元素,因为默认堆顶元素为最大或最小值，直接取出即可
            int tt = num[j];
            num[j] = num[0];
            num[0] = tt;
            adjustHeap(num, 0, j);
        }

        return num;
    }

    private static void adjustHeap(int[] num, int i, int length) {
        int temp = num[i];

        // 遍历方式为从2*i+1开始，到length
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            // 如果左子节点小于右子节点，则指向右子节点
            if (k + 1 < length && num[k] < num[k + 1]) {
                k++;
            }
            // 如果遍历元素比当前元素大，则交换
            if (num[k] > temp) {
                num[i] = num[k];
                i = k;
            } else {
                break;
            }
        }
        // 放置最终取出来的值
        num[i] = temp;
    }
}

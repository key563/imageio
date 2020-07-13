package com.key.imageio.aop;

/**
 * @author tujia
 * @since 2020/7/9 21:47
 */
public class TT {

    public static void main(String[] args) {
        int mm = 1;
        int m = 1;
        System.out.println(++mm);
        System.out.println(m++);
        System.out.println(func(5, 26));
    }

    private static double func(int n, int m) {
        // 默认n<m，如果n>m则调换顺序传入参数
        if (Math.pow(n, 2) > m) {
            double x = Math.pow(n, 2) - m + 1;
            double y = m - n;
            return x > y ? y : x;
        } else {
            double x = m - Math.pow(n, 2) + 1;
            double y = m - n;
            return x > y ? y : x;
        }
    }


    public static int plus(int n, int m) {
        return 0;
    }
}




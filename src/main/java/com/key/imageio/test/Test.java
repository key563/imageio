package com.key.imageio.test;

/**
 * @author tujia
 * @since 2020/7/10 9:41
 */
public class Test extends PP {
    private static final int counter = 10;
    public void count(int i) {
        System.out.println(10 % i);
    }
    public void eat(){}


    public static void main(String[] args) {
        Test tt = new Test();
        tt.count(3);
//        System.out.println(++counter);
    }
}

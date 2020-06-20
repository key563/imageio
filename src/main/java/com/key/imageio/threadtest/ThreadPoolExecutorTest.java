package com.key.imageio.threadtest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tujia
 * @since 2020/2/19 11:11
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        test1();
    }
    public static void test1() {

        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(2);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.HOURS, queue, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 10; i++) {
            final int index = i;
            System.out.println("task--" + i);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread start:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("thread end:" + (index + 1));
                }
            };
            executor.execute(runnable);
            if (index == 6) {
                System.out.println("sss");
            }
        }
    }

}

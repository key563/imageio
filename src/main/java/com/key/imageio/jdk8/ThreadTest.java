package com.key.imageio.jdk8;

import sun.font.TextRecord;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author tujia
 * @since 2020/2/26 10:00
 */
public class ThreadTest {

    public void go() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Random random = new Random();

        pool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "开始执行任务");
                    Thread.sleep(random.nextInt(100));
                    System.out.println(Thread.currentThread().getName() + "结束执行任务");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        pool.shutdown();
    }
}

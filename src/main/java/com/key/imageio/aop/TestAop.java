package com.key.imageio.aop;

import com.key.imageio.web.service.IUserService;
import com.key.imageio.web.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tujia
 * @since 2020/6/22 9:54
 */
public class TestAop {
    public static void main(String[] args) {
//        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        IUserService userService = context.getBean(UserServiceImpl.class);
//        userService.createUser("zhangsan",22);
//        userService.queryUser();
        test();
    }

    public static void test() {
//        int[] arr = new int[]{1, 4, 8, 6, 9};
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = i + 1; j < arr.length; j++) {
//                if (arr[i] > arr[j]) {
//                    int a = arr[j];
//                    arr[j] = arr[i] = a;
//                }
//            }
//        }
//        for (int a : arr) {
//            System.out.println(a);
//        }

        int nn = -1<<1;
        System.out.println(nn);
//        for (int i = 1; i < 10; i++) {
//            for (int j = 1; j <= i; j++) {
//                System.out.print(i + "*" + j + "=" + (i * j) + "\t");
//            }
//            System.out.println();
//        }
    }
}

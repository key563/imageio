package com.key.imageio.aop;

import com.key.imageio.web.entity.Person;
import com.key.imageio.web.service.IUserService;
import com.key.imageio.web.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

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
//        test();
//        Integer ss = null;
//        String mm = String.valueOf(ss);
//        String sss = null;
//        System.out.println(mm);
//        System.out.println(sss);
//        List<Integer> list = new ArrayList<>();
        TestAop testAop = new TestAop();
        ListNode l1 = testAop.listToNode(342);
        ListNode l2 = testAop.listToNode(465);
        ListNode result = testAop.addTwoNumbers(l1, l2);
        System.out.println(testAop.num(testAop.ss(result)));

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> s1 = ss(l1);
        List<Integer> s2 = ss(l2);

        Integer n1 = num(s1);
        Integer n2 = num(s2);

        System.out.println(n1 + n2);
        return listToNode(n1 + n2);
    }

    public List<Integer> ss(ListNode listNode) {
        List<Integer> s1 = new ArrayList<>();
        while (listNode != null) {
            s1.add(listNode.val);
            listNode = listNode.next;
        }
        return s1;
    }

    public ListNode listToNode(Integer num) {
        String[] nn = num.toString().split("");
        ListNode node = null;
        for (String c : nn) {
            ListNode l = new ListNode(new Integer(c), node);
            node = l;
        }
        return node;
    }

    public Integer num(List<Integer> list) {
        int num = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            int n = list.get(i) * Double.valueOf(Math.pow(10, i)).intValue();
            num = num + n;
        }
        return num;
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

        HashMap<Person, String> map = new HashMap<>();
        List<Person> list = new ArrayList<>(16);
        for (Integer i = 0; i < 15; i++) {
            Person p = new Person();
            p.setAge(i);
            if (i < 12) {
                p.setAge(130);
                map.put(p, i.toString());
                if (i > 7) {
                    System.out.println(map);
                }
//                if (i > 9) {
//                    System.out.println(map.remove(p));
//                }
            } else {
                map.put(p, i.toString());
            }
            list.add(p);
        }


        for (int i = 0; i < 7; i++) {
            map.remove(list.get(i));
            System.out.println(map);
        }

        System.out.println(map);


//        for (int i = 1; i < 10; i++) {
//            for (int j = 1; j <= i; j++) {
//                System.out.print(i + "*" + j + "=" + (i * j) + "\t");
//            }
//            System.out.println();
//        }
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

}

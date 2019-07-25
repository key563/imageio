package com.key.imageio.jdk8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BooleanSupplier;

/**
 * Lambda表达式 基本概念
 * = 匿名函数（包含参数和函数体，函数体可以是表达式或函数语句）
 * = (Parameters) -> { body }
 * <p>
 * 特点：
 * 1.不需要在匿名类去实现接口
 * 2.不需要申明参数的类型javac会根据上下文去自动获取相对应的类型
 * <p>
 * 注意点：
 * 1.lambda表达式主体可能有局部变量语句。可以在lamda表达体中使用break,continue和return等，甚至可以抛异常
 * 2.lambda表达式没有名称，因为它表示的是匿名内部类（unnamed expression）
 * 3.lambda表达式的返回类型是有编译器推断的
 * 4.lambda表达式不能像一个方法一样使用throws子句抛异常
 * 5.在通用功能界面中定义了lambda表达式，但不能通用
 *
 * @author tujia
 * @date 2019/6/24 11:19
 */
public class FunctionalInterfaceTest {
    public static void main(String[] argv) {
        // 注意参数类型
        //实现接口方法
        Processor stringProcessor = (String str) -> str.length();
        SecondProcessor secondProcessor = (String str) -> str.length();
        //stringProcessor = secondProcessor; //compile error
        String name = "stringProcessor";
        int length = stringProcessor.getStringLength(name);
        System.out.println(length);

        String name2 = "secondProcessor2";
        int length2 = secondProcessor.noName(name2);
        System.out.println(length2);
        new Thread(() -> System.out.println("new Thread")).start();

    }

    /**
     * 无参类型
     */
    public static void noParameter() {
        BooleanSupplier bs = () -> true;
        System.out.println(bs.getAsBoolean());

        int x = 0, y = 1;
        bs = () -> x > y;
        System.out.println(bs.getAsBoolean());
    }

    public static void demo() {
        String[] players = {"Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer", "Andy Murray", "Tomas Berdych", "Juan Martin Del Potro", "Richard Gasquet", "John Isner"};

        // 遍历输出数组元素
        System.out.print("Show the list of players:\n");
        Arrays.asList(players).forEach((player) -> System.out.println(player));

        // 实现compare接口自定义排序
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.compareTo(s2));
            }
        });
        // lambda表达式实现自定义排序
        Comparator<String> sortByName = (String s1, String s2) -> (s1.compareTo(s2));
        Arrays.sort(players, sortByName);
        Arrays.sort(players, (String s1, String s2) -> (s1.compareTo(s2)));

        System.out.print("\nShow the list of players after sorting by name:\n");
        Arrays.asList(players).forEach((player) -> System.out.println(player));

        // 根据姓开始字母顺序进行排序，使用内部类
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.substring(s1.indexOf(" ")).compareTo(s2.substring(s2.indexOf(" "))));
            }
        });

        // 根据姓开始字母顺序进行排序，使用lambda表达式
        Comparator<String> sortBySurname = (String s1, String s2) -> (s1.substring(s1.indexOf(" ")).compareTo(s2.substring(s2.indexOf(" "))));
        Arrays.sort(players, sortBySurname);
        // or
        Arrays.sort(players, (String s1, String s2) -> (s1.substring(s1.indexOf(" ")).compareTo(s2.substring(s2.indexOf(" ")))));

        System.out.print("\nShow the list of players after sorting by surname:\n");
        Arrays.asList(players).forEach((player) -> System.out.println(player));

        // 根据名开始字母顺序进行排序，使用匿名内部类
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.length() - s2.length());
            }
        });

        // 根据名开始字母顺序进行排序，使用lambda表达式
        Comparator<String> sortByNameLenght = (String s1, String s2) -> (s1.length() - s2.length());
        Arrays.sort(players, sortByNameLenght);
        // or this
        Arrays.sort(players, (String s1, String s2) -> (s1.length() - s2.length()));

        System.out.print("\nShow the list of players after sorting by length:\n");
        Arrays.asList(players).forEach((player) -> System.out.println(player));

        // Sort players by last letter using anonymous innerclass
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1));
            }
        });

        // Sort players by last letter using lambda expression
        Comparator<String> sortByLastLetter = (String s1, String s2) -> (s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1));
        Arrays.sort(players, sortByLastLetter);
        // or this
        Arrays.sort(players, (String s1, String s2) -> (s1.charAt(s1.length() - 1) - s2.charAt(s2.length() - 1)));

        System.out.print("\nShow the list of players after sorting by last letter:\n");
        Arrays.asList(players).forEach((player) -> System.out.println(player));
    }

    @FunctionalInterface
    interface Processor {
        int getStringLength(String str);
    }

    @FunctionalInterface
    interface SecondProcessor {
        int noName(String str);
    }
}



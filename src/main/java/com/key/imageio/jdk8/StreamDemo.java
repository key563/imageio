package com.key.imageio.jdk8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author tujia
 * @date 2019/6/24 11:19
 * <p>
 * <p>
 * Stream 基本概念
 * 以声明方式处理数据，并利用多核架构，而无需为其编写任何特定的代码。
 * stream并不是某种数据结构，它只是数据源的一种视图。这里的数据源可以是一个数组，Java容器或I/O channel等。
 * <p>
 * 特点：
 * 1.无存储，stream不是一种数据结构，只是某种数据源的一个视图，数据源可以是一个数组，Java容器或I/O channel等。
 * 2.为函数式编程而生，对stream的任何修改都不会修改背后的数据源。如对stream执行过滤操作并不会删除被过滤的元素，而是会
 * 产生一个不包含被过滤元素的新stream（重新生成+副本）
 * 3.惰性执行，stream上的操作并不会立即执行，只有等到用户真正需要结果的时候才会执行。（中间操作和结束操作）
 * 4.可消费性，stream只能被“消费”一次，一旦遍历过就会失效，就像容器的迭代器一样，想要再次遍历就必须重新生成。
 * 概念：
 * 中间操作：总是会惰性执行，调用中间操作只会生成一个标记了该操作的新stream
 * 常见接口方法：concat(),distinct(),filter(),flatMap(),limit(),map(),peek(),skip(),sorted(),parallel(),sequential(),unordered()
 * 结束操作：会触发实际计算，计算发生时会把所有中间操作积攒的操作以pipeline的方式执行，可以减少迭代次数，计算完成后
 * stream就会失效。<p>故不要在结束操作之后再调用中间操作!!!</p>
 * 常见接口方法：allMatch(),anyMatch(),noneMatch(),collect(),count(),findAny(),findFirst(),forEach(),forEachOrdered(),max(),min(),reduce(),toArray()
 */
public class StreamDemo {
    public static void main(String[] args) throws Exception {
//        flatMapTest();
//        sort();
//        minMax();
//        supplier();
//        iterate();
//        sideEffect();
        testss();
//        List<String> l = new ArrayList(Arrays.asList("one", "two"));
//        Stream<String> sl = l.stream();
//        Stream<String> s2 = l.stream();
//        l.add("three");
//        String s = sl.collect(joining(" "));
//        l.add("four");
//        String s1 = s2.collect(joining(" "));
//
//        System.out.println(s);
//        System.out.println(s1);
//
//        int[] a = IntStream.range(0, 5).parallel().map(x -> x * 2).toArray();
//        Arrays.stream(a).forEach(System.out::println);


    }

    /**
     * 基于Stream实现9*9乘法表
     */
    public static void mm() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.forEach(i -> {
                    list.stream()
                            .filter(j -> j <= i)    // 过滤递减
                            .forEach(k -> System.out.print(k + " * " + i + " = " + k * i + ";\t"));
                    System.out.println();
                }
        );
    }

    public static void flatMapTest() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
//        Stream<Integer> outputStream = inputStream.
//                flatMap((childList) -> childList.stream());
        List<Integer> nums = inputStream.flatMap(num -> num.stream()).map(s -> s * s).collect(Collectors.toList());
        nums.forEach(System.out::println);
    }

    public static List<Human> createList(int i) {
        List<Human> humanList = new ArrayList<>();
        for (int j = i; j >= 0; j--) {
            Human human = new Human(i - j, "zhang" + j);
            humanList.add(human);
        }
        return humanList;
    }

    public static void sort() {
        // 自定义排序
        List<Human> humanList = createList(10);
        humanList.forEach(System.out::println);
        // 默认排序，实现Comparable接口的compareTo方法
        humanList.stream().sorted().forEach(System.out::println);
        // 自定义排序
        humanList.stream().sorted((h1, h2) -> h1.getName().compareTo(h2.getName())).forEach(System.out::println);
        // 同上
        humanList.stream().sorted(Comparator.comparing(Human::getName)).forEach(System.out::println);

    }

    public static void minMax() throws IOException {
        String fileName = "D:\\jdk_learning\\words.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        // 计算最长的一行
        int maxLength = br.lines().mapToInt(String::length).max().getAsInt();
        System.out.println("最长一行长度:" + maxLength);

        br = new BufferedReader(new FileReader(fileName));
        int minLength = br.lines().mapToInt(String::length).min().getAsInt();
        System.out.println("最短一行长度:" + minLength);

        br = new BufferedReader(new FileReader(fileName));
        int maxWordNum = br.lines().mapToInt(line -> line.split(" ").length).max().getAsInt();
        System.out.println("单行单词数最多为:" + maxWordNum);

        br = new BufferedReader(new FileReader(fileName));
        int minWordNum = br.lines().mapToInt(line -> line.split(" ").length).min().getAsInt();
        System.out.println("单行单词数最少为:" + minWordNum);

        // 计算单词个数，去重
        br = new BufferedReader(new FileReader(fileName));
        List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" "))).map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());
        System.out.println("单词总数量为：" + words.size());

        // 输出每行单词数
        br = new BufferedReader(new FileReader(fileName));
        System.out.println("每行单词数量为:");
        br.lines().map(line -> line.split(" ").length).forEach(System.out::println);
        br.close();
    }

    public static void supplier() {
        // 通过自定义Supplier实现自定义规则生成海量测试数据
        Stream.generate(new HumanSupplier()).limit(100).forEach(System.out::println);
    }

    public static void iterate() {
        // 聚合操作的一种方式，接受一个种子，然后在遍历每个元素的时候将上一个元素的结果传入下个个元素作为种子
        // 生成2的倍数
        Stream.iterate(1, n -> n * 2).limit(10).forEach(System.out::println);

        Integer seed = 1;
        for (int i = 0; i < 10; i++) {
            seed = 2 ^ i;
            System.out.println(seed.intValue());
        }
    }

    public static void bufferedReaderToStream() throws FileNotFoundException {
        // 读取文件流进行Stream操作
        String fileName = "D:\\jdk_learning\\words.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" "))).filter(word -> word.length() > 0).collect(Collectors.toList());
        words.forEach(System.out::println);
    }


    public static void lamdaTest() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            strings.add("str_" + i);
        }
        long start1 = System.currentTimeMillis();
//        strings.forEach(str -> System.out.println(str));
        strings.parallelStream().forEach(System.out::println);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        for (String str : strings) {
            System.out.println(str);
        }
        long end2 = System.currentTimeMillis();
        Iterator<String> stringIterator = strings.iterator();
        while (stringIterator.hasNext()) {
            System.out.println(stringIterator.next());
        }
        long end3 = System.currentTimeMillis();
        System.out.println("lamda play times :" + (end1 - start1));
        System.out.println("for loop play times :" + (end2 - start2));
        System.out.println("iterator play times :" + (end3 - end2));

        List<String> strs = strings.stream().filter(str -> str.equals("str_1")).collect(Collectors.toList());
        System.out.println(strs.size());
//        System.out.println(stream.count());
        // 流（Stream）仅仅代表着数据流，并没有数据结构，所以遍历完一次之后便再也无法遍历
//        stream.forEach(str -> System.out.println(str));
    }

    public void streamTest() {
        long t0 = System.nanoTime();
        //初始化一个范围100万整数流,求能被2整除的数字，toArray（）是终点方法
        int a[] = IntStream.range(0, 1_000_000).filter(p -> p % 2 == 0).toArray();
        long t1 = System.nanoTime();
        //和上面功能一样，这里是用并行流来计算
        int b[] = IntStream.range(0, 1_000_000).parallel().filter(p -> p % 2 == 0).toArray();
        long t2 = System.nanoTime();
        System.out.printf("serial: %.2fs, parallel %.2fs%n", (t1 - t0) * 1e-9, (t2 - t1) * 1e-9);
    }

    public static void sideEffect() {
        // 副作用操作
        // 官方文档是这样描述的：如果在执行stream过程中需要进行side-effects操作，不能保证在不同的线程中执行具有相同的结果，
        //      在许多计算中，人们可能会倾向于使用副作用，但这些副作用可以更安全、更有效地表达，比如使用reduction而不是可变累加器，
        //      一部分的stream操作可以使用side-effcets，例如forEach,peek等，但也需要小心使用
        ArrayList<Human> results = new ArrayList<>();
        List<Human> strings = createList(10);
        Pattern pattern = Pattern.compile("zhang.*");
        // 不支持，线程不安全
        strings.stream().filter(s -> pattern.matcher(s.getName()).matches())
                .forEach(s -> results.add(s));  // Unnecessary use of side-effects!
        // 支持
        List<Human> result = strings.stream().filter(s -> pattern.matcher(s.getName()).matches())
                .collect(Collectors.toList());  // No side-effects!
        results.forEach(System.out::println);
        result.forEach(System.out::println);
    }

    /**
     * 封装运行时异常捕获
     *
     * @param checkedFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<T, R> wrap(CheckedFunction<T, R> checkedFunction) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public Human doOpt(Human human) {
        return human;
    }

    public static int getWords(Object line) {
        String lines = (String) line;
        return lines.split(" ").length;
    }

    public static void testss() throws FileNotFoundException {
        String fileName = "D:\\jdk_learning\\words.txt";
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        int maxWordNum = br.lines().map(wrap(line -> getWords(line))).mapToInt(line -> line.intValue()).max().getAsInt();
        System.out.println("单行单词数最多为:" + maxWordNum);
    }


}

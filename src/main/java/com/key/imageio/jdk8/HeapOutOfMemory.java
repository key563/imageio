package com.key.imageio.jdk8;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出测试
 *
 * @author tujia
 * @since 2020/6/11 9:16
 */
public class HeapOutOfMemory {

    /**
     * 测试vm配置
     * -verbose:gc -Xms10M -Xmx10M -XX:MaxDirectMemorySize=5M -Xss160k -XX:+PrintGCDetails
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello HeapOutOfMemory");
        List<Person> persons = new ArrayList<>();
        int counter = 0;
        while (true) {
            persons.add(new Person());
            System.out.println("Instance :" + (++counter));
        }
    }
}

/**
 * 测试执行结果如下：
 * [Full GC (Ergonomics) [PSYoungGen: 1875K->669K(2560K)] [ParOldGen: 7130K->7130K(7168K)] 9005K->7800K(9728K), [Metaspace: 3410K->3410K(1056768K)], 0.0557501 secs] [Times: user=0.45 sys=0.00, real=0.05 secs]
 * [Full GC (Allocation Failure) Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at java.util.Arrays.copyOf(Unknown Source)
 * 	at java.util.Arrays.copyOf(Unknown Source)
 * 	at java.util.ArrayList.grow(Unknown Source)
 * 	at java.util.ArrayList.ensureExplicitCapacity(Unknown Source)
 * [PSYoungGen: 669K->669K(2560K)] [ParOldGen: 7130K->7130K(7168K)] 7800K->7800K(9728K), [Metaspace: 3410K->3410K(1056768K)], 0.0580796 secs] [Times: user=0.31 sys=0.00, real=0.06 secs]
 * Heap
 * 	at java.util.ArrayList.ensureCapacityInternal(Unknown Source)
 *  PSYoungGen      total 2560K, used 744K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
 * 	at java.util.ArrayList.add(Unknown Source)
 *   eden space 2048K, 36% used [0x00000000ffd00000,0x00000000ffdba2a8,0x00000000fff00000)
 * 	at com.key.imageio.jdk8.HeapOutOfMemory.main(HeapOutOfMemory.java:25)
 *   from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
 *   to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
 *  ParOldGen       total 7168K, used 7130K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
 *   object space 7168K, 99% used [0x00000000ff600000,0x00000000ffcf6908,0x00000000ffd00000)
 *  Metaspace       used 3440K, capacity 4500K, committed 4864K, reserved 1056768K
 *   class space    used 379K, capacity 388K, committed 512K, reserved 1048576K
 */
class Person {

}
package com.key.imageio.jdk8;

import java.util.Random;
import java.util.function.Supplier;

/**
 * 自定义Supplier
 * 通过重写get方法进行自定义数据生成规则
 */
public class HumanSupplier implements Supplier<Human> {

    private int index;
    private Random seed = new Random();

    @Override
    public Human get() {
        return new Human(++index, "generateHuman" + index, seed.nextInt(100));
    }
}

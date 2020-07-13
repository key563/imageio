package com.key.imageio.web.entity;


/**
 * @author tujia
 * @since 2020/7/1 10:37
 */
public class Person {
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        return age;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

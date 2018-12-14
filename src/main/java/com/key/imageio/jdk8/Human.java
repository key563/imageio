package com.key.imageio.jdk8;

import org.jetbrains.annotations.NotNull;

public class Human implements Comparable<Human> {
    private int id;
    private String name;
    private int age;
    private String address;

    public Human(String name) {
        this.name = name;
    }

    public Human(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Human(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(@NotNull Human o) {
        return this.getId() - o.getId();
    }

    @Override
    public String toString() {
        return id + ":" + name + ":" + age;
    }
}

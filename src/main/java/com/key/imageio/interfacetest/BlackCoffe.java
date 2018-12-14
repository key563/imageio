package com.key.imageio.interfacetest;

public class BlackCoffe implements Coffe{
    @Override
    public void start() {
        System.out.println("BlackCoffe start");
    }

    @Override
    public void play() {
        System.out.println("BlackCoffe play method things");
    }

    @Override
    public void end() {
        System.out.println("BlackCoffe start");
    }
}

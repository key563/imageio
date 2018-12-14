package com.key.imageio.interfacetest;

public interface Coffe {
    void start();
    default void play(){
        System.out.println("Default play method things");
        start();
        end();
    }
    void end();
}

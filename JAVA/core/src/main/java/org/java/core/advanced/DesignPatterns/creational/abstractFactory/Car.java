package org.java.core.advanced.DesignPatterns.creational.abstractFactory;

public abstract class Car {
    private String name;

    public Car(String name) {
        this.name = name;
    }

    void drive() {
        System.out.println(this.name + " drive");
    }
}

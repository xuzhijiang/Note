package org.java.core.DesignPatterns.abstractFactory;

public abstract class Car {
    private String name;

    public Car(String name) {
        this.name = name;
    }

    void drive() {
        System.out.println(this.name + " drive");
    }
}

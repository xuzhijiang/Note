package org.java.core.DesignPatterns.simpleFactory;

public abstract class Car {
    private String name;

    public Car(String name) {
        this.name = name;
    }

    void drive() {
        System.out.println(this.name + " drive");
    }
}

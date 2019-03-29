package org.java.core.advanced.DesignPatterns.structural.decorator;

public class Decat extends Beverage {
    public Decat() {
        description = "这是一杯深焙咖啡";
    }
    @Override
    public double cost() {
        return 0.99;
    }
}
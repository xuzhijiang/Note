package org.java.core.advanced.DesignPatterns.structural.decorator.demo01;

public class Espresso extends Beverage{
    public Espresso() {
        description = "这是一杯浓缩咖啡";
    }
    @Override
    public double cost() {
        return 1.99;
    }
}
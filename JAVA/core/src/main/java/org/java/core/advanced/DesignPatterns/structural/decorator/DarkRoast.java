package org.java.core.advanced.DesignPatterns.structural.decorator;

public class DarkRoast extends Beverage{
    public DarkRoast() {
        description = "这是一杯低咖啡因咖啡";
    }
    @Override
    public double cost() {
        return 1.05;
    }
}
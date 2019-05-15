package org.java.core.advanced.DesignPatterns.structural.decorator.demo01;

public class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "这是一杯综合咖啡";
    }
    @Override
    public double cost() {
        return 0.89;
    }
}
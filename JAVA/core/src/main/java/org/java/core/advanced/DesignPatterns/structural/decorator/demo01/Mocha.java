package org.java.core.advanced.DesignPatterns.structural.decorator.demo01;

public class Mocha extends CondimentDecorator {
    Beverage beverage;
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+",摩卡";
    }

    @Override
    public double cost() {
        return beverage.cost()+0.2;
    }
}
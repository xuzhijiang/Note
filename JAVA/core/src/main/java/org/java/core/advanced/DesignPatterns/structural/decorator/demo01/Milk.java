package org.java.core.advanced.DesignPatterns.structural.decorator.demo01;

public class Milk extends CondimentDecorator{
    Beverage beverage;
    public Milk(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+",牛奶";
    }

    @Override
    public double cost() {
        return beverage.cost()+0.1;
    }
}
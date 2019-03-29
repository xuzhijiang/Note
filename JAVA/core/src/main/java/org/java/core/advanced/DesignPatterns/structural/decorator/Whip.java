package org.java.core.advanced.DesignPatterns.structural.decorator;

public class Whip extends CondimentDecorator{
    Beverage beverage;
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
    @Override
    public String getDescription() {
        return beverage.getDescription()+",奶泡";
    }
    @Override
    public double cost() {
        return beverage.cost()+0.1;
    }
}
package org.java.core.advanced.DesignPatterns.structural.decorator;

/**
 * 购买咖啡时，会加入不同的调料，根据不同的调料来收费，
 * 也就是说不同的咖啡与不同的调料有N中不同的组合方式，也就是出现了不同组合就应该有不同的价格
 */
public class StartbuzzCoffee {
    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription()+",$"+beverage.cost());
        System.out.println("========================");

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription()+",$"+beverage2.cost());
    }
}
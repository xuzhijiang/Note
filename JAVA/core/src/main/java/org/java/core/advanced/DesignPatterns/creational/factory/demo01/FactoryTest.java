package org.java.core.advanced.DesignPatterns.creational.factory.demo01;

public class FactoryTest {

    public static void main(String[] args) {
        SimplyPizzaFactory factory = new SimplyPizzaFactory();
        PizzaStore store = new PizzaStore(factory);
        // 可以根据传递的参数不同，返回不同类的实例
        Pizza pizza = store.orderPizza("pepperoni");
        System.out.println("pizza: " + pizza.toString());
    }
}

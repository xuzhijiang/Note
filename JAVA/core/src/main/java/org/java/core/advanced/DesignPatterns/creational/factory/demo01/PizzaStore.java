package org.java.core.advanced.DesignPatterns.creational.factory.demo01;

public class PizzaStore {

    SimplyPizzaFactory factory;

    public PizzaStore(SimplyPizzaFactory factory) {
        this.factory = factory;
    }

    public Pizza orderPizza(String type){
        Pizza pizza;
        pizza = factory.createPizza(type);//采用工厂对象的创建方法实例化
        pizza.prepare();
        pizza.bak();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}